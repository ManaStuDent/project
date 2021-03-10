package com.manastudent.admin.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.manastudent.db.servie.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AccessDecisionProcessor implements AccessDecisionVoter<FilterInvocation> {

    Log log = LogFactory.get();

    @Autowired
    ResourceService resourceService;

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        String requestUrl = object.getRequestUrl();
        log.info("AccessDecisionProcessor 权限拦截，request_url: {}", requestUrl);

        if (requestUrl.equals("/login")) {
            return ACCESS_GRANTED;
        }

        // 从资源列表查询出资源对应的所有的角色
        String roles = resourceService.queryRoleNameByResource(requestUrl);
        if (StrUtil.isEmpty(roles)) {
            return ACCESS_ABSTAIN;
        }

        Set<String> rolesSet = Arrays.stream(roles.split(",")).collect(Collectors.toSet());
        Set<String> userRoleSet = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.replace("ROLE_", ""))
                .collect(Collectors.toSet());
        rolesSet.retainAll(userRoleSet);

        // 如果为空则说明没有交集 -> 没有权限
        if (rolesSet.isEmpty()) {
            return ACCESS_ABSTAIN;
        }

        return ACCESS_GRANTED;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
