package com.manastudent.db.servie.impl;

import com.manastudent.db.dao.ex.UserMapperEx;
import com.manastudent.db.domain.Role;
import com.manastudent.db.domain.User;
import com.manastudent.db.servie.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapperEx userMapperEx;

    /**
     * 登陆授权过后获取用户信息
     */
    public User getCurrentUser() {
        return this.findByLoginName(getCurrentUserName().orElseThrow());
    }

    /**
     * 登陆授权过后获取用户登录名
     */
    public Optional<String> getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return Optional.of((String) authentication.getPrincipal());
        }
        return Optional.empty();
    }

    @Override
    public User findById(int id) {
        return userMapperEx.findByid(id);
    }

    @Override
    public User findByLoginName(String loginName) {
        return userMapperEx.findByLoginName(loginName);
    }

    @Override
    public User findByIdWithMapper(int id) {
        return userMapperEx.findByIdWithMapper(id);
    }

    @Override
    public List<Role> getAllRoles(int id) {
        return userMapperEx.getAllRoles(id);
    }
}
