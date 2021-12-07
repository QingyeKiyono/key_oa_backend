package com.key.oa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * Hello world，用于测试项目能否正常启动
 */
@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }
}
