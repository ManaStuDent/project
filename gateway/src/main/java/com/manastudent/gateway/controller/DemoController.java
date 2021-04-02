package com.manastudent.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${name}")
    private String name;

    @GetMapping("/echo/{msg}")
    public String echo(@PathVariable String msg) {
        System.out.println("nacos config: name->" + name);
        return restTemplate.getForObject("http://project-admin-api/admin/public/echo/" + msg, String.class);
    }
}
