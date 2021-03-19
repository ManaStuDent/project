package com.manastudent.admin.controller.demo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.pagehelper.PageHelper;
import com.manastudent.admin.config.AuthService;
import com.manastudent.admin.dto.LoginRequest;
import com.manastudent.core.config.RabbitMQConfig;
import com.manastudent.core.util.CacheUtils;
import com.manastudent.core.util.JacksonUtil;
import com.manastudent.core.util.RedisUtils;
import com.manastudent.core.util.SecurityConstants;
import com.manastudent.db.domain.User;
import com.manastudent.db.servie.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Validated
public class DemoController {

    Log log = LogFactory.get();

    private final UserService userService;
    private final AuthService authService;
    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/json")
    public String json() {
        log.info("访问 /json");
        User user = new User();
        user.setId(1);
        user.setName("LiuChenglong");
        user.setCreateTime(LocalDateTime.now());
        String json = JacksonUtil.obj2String(user);

        return json;
    }

    @GetMapping("/query")
    public User queryDb(@NotNull(message = "id 不能为空") Integer id) {

        // 分页
        PageHelper.startPage(1, 1);
        User user = userService.findById(1);

        // 注解查询
        User user2 = userService.findByIdWithMapper(1);

        return user;
    }

    @GetMapping("redis")
    public User redisTest() {
        String tempJson = RedisUtils.get("user:query:1");
        if (StrUtil.isNotEmpty(tempJson)) {
            return JacksonUtil.string2Obj(tempJson, User.class);
        }

        User user = userService.findByIdWithMapper(1);
        RedisUtils.set("user:query:1", JacksonUtil.obj2String(user), 10L);
        return user;
    }

    @GetMapping("cache")
    public User cacheTest() {
        String tempJson = CacheUtils.get("user:query:1");
        if (StrUtil.isNotEmpty(tempJson)) {
            return JacksonUtil.string2Obj(tempJson, User.class);
        }

        User user = userService.findByIdWithMapper(1);
        CacheUtils.set("user:query:1", JacksonUtil.obj2String(user));
        return user;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Validated LoginRequest loginRequest) {
        String token = authService.createToken(loginRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, token);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.removeToken();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/rabbitMq")
    public ResponseEntity<Void> rabbitMqTest() {

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME_NOTIFY, "notify.hello", "hello");

        return null;
    }
}
