package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.example.demo.entity.HelloRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @PostMapping("/helloworld")
    public String helloWorld(@RequestBody HelloRequest request) {
        if (StrUtil.isBlank(request.getName()) || StrUtil.isBlank(request.getSay())) {
            return "参数不能为空";
        }
        return StrUtil.format("{}, 你好，我想说：{}", request.getName(), request.getSay());
    }
}
