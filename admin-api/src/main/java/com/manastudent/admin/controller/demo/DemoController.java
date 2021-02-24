package com.manastudent.admin.controller.demo;

import com.manastudent.core.util.JacksonUtil;
import com.manastudent.admin.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DemoController {


    @GetMapping("/demo")
    public String demo() {

        User user = new User();
        user.setId(1);
//        user.setName("LiuChenglong");
        user.setValidator(true);
        user.setCreateDate(new Date());

        String json = JacksonUtil.obj2String(user);

        return json;
    }
}
