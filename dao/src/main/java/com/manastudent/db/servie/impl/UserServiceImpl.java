package com.manastudent.db.servie.impl;

import com.manastudent.db.dao.ex.UserMapperEx;
import com.manastudent.db.domain.User;
import com.manastudent.db.servie.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapperEx userMapperEx;

    @Override
    public User findById(int id) {
        return userMapperEx.findByid(id);
    }
}
