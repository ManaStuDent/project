package com.manastudent.admin.service;

import com.manastudent.api.DemoService;
import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.CompletableFuture;


@Service
public class DubboProviderService implements DemoService {
    @Override
    public String sayHello(String msg) {
        return "hello " + msg;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String msg) {
        return CompletableFuture.supplyAsync(() -> "hello " + msg);
    }
}
