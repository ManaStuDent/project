package com.manastudent.admin.service;

import com.manastudent.api.DemoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.CompletableFuture;


@Service
public class DubboProviderService implements DemoService {

    @Value("${server.port}")
    private String port;

    @Override
    public String sayHello(String msg) {
        return "hello " + msg + " " + port;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String msg) {
        return CompletableFuture.supplyAsync(() -> "hello " + msg + " " + port);
    }
}
