package com.manastudent.admin.controller.demo;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.pagehelper.PageHelper;
import com.manastudent.core.util.JacksonUtil;
import com.manastudent.db.domain.User;
import com.manastudent.db.servie.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class DemoController {

    Log log = LogFactory.get();


    @Autowired
    UserService userService;

    @GetMapping("/json")
    public String json() {
        log.info("访问 /json");
        User user = new User();
        user.setId(1);
        user.setName("LiuChenglong");
        user.setCreatedate(LocalDateTime.now());
        String json = JacksonUtil.obj2String(user);

        return json;
    }

    @GetMapping("/query")
    public User queryDb() {

        // 分页
        PageHelper.startPage(1, 1);
        User user = userService.findById(1);

        // 注解查询
        User user2 = userService.findByIdWithMapper(1);

        return user;
    }
}
