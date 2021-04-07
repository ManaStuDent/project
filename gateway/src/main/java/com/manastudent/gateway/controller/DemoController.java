package com.manastudent.gateway.controller;

import com.manastudent.api.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@RestController
@RefreshScope
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    @Reference(timeout = 1000)
    private DemoService demoService;

    @Value("${name}")
    private String name;

    @GetMapping("/echo/{msg}")
    public String echo(@PathVariable String msg) {
        System.out.println("nacos config: name->" + name);
        return restTemplate.getForObject("http://project-admin-api/admin/public/echo/" + msg, String.class);
    }

    @GetMapping("/dubbo/{msg}")
    public String dubboDemo(@PathVariable String msg) {
        return demoService.sayHello(msg);
    }

    /**
     * 在 Dubbo 中发起异步调用
     */
    @GetMapping("/dubboAsync/{msg}")
    public String dubboAsyncDemo(@PathVariable String msg) {
        // 调用直接返回CompletableFuture
        CompletableFuture<String> future = demoService.sayHelloAsync("async call request");
        // 增加回调
        future.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println("Response: " + v);
            }
        });
        // 早于结果输出
        System.out.println("Executed before response return.");

        return demoService.sayHello(msg);
    }
}
