package com.manastudent.admin.service;

import com.manastudent.api.DemoService;
import org.apache.dubbo.config.annotation.Service;


@Service
public class DubboProviderService implements DemoService {
    @Override
    public String sayHello(String msg) {
        return "hello " + msg;
    }
}
