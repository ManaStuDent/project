package com.manastudent.api;

import java.util.concurrent.CompletableFuture;

public interface DemoService {
    String sayHello(String msg);

    CompletableFuture<String> sayHelloAsync(String name);
}
