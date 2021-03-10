package com.manastudent.admin.config;

import com.manastudent.admin.dto.LoginRequest;
import com.manastudent.core.util.JwtTokenUtils;
import com.manastudent.core.util.RedisUtils;
import com.manastudent.db.domain.Role;
import com.manastudent.db.domain.User;
import com.manastudent.db.jwt.JwtUser;
import com.manastudent.db.servie.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    UserService userService;


    public String createToken(LoginRequest loginRequest) {
        String loginName = loginRequest.getLoginName();
        String password = loginRequest.getPassword();

        User user = userService.findByLoginName(loginName);

        if (null == user || !user.getPassword().equals(password)) {
            throw new BadCredentialsException("The user name or password is not correct.");
        }

        List<Role> allRoles = userService.getAllRoles(user.getId());
        List<SimpleGrantedAuthority> _authorities = new ArrayList<>();
        allRoles.forEach(role -> _authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));

        JwtUser jwtUser = new JwtUser(user, _authorities, allRoles);
        if (!jwtUser.isEnabled()) {
            throw new BadCredentialsException("User is forbidden to login");
        }
        List<String> authorities = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = JwtTokenUtils.createToken(user.getName(), user.getId().toString(), authorities, false);
        RedisUtils.set(user.getId().toString(), token);
        return token;

    }

    public void removeToken() {
        String userName = userService.getCurrentUserName().orElseThrow();
        RedisUtils.delete(userName);
    }

}
