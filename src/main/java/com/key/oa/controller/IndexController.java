package com.key.oa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 孙强
 * 用做测试接口
 */
@RestController
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "Hello, world!";
    }
}
