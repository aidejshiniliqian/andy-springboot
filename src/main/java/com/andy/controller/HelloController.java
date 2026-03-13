package com.andy.controller;

import com.andy.entity.HelloRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloworld")
public class HelloController {

    @PostMapping
    public String hello(@RequestBody HelloRequest request) {
        return request.getName() + ", 你好，我想说：" + request.getSay();
    }

}
