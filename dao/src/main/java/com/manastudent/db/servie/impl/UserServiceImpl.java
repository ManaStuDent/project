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

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapperEx userMapperEx;

    public User getCurrentUser() {
        return this.findByLoginName(getCurrentUserName());
    }

    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
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
