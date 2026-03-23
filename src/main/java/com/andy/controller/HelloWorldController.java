package com.andy.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.andy.dto.HelloRequest;

@RestController
public class HelloWorldController {

    @PostMapping("/helloworld")
    public String helloworld(@RequestBody HelloRequest request) {
        return String.format("%s, 你好，我想说：%s", request.getName(), request.getSay());
    }
}
