package com.key.oa.controller;

import com.key.oa.common.JsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * Hello world，用于测试项目能否正常启动
 */
@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public JsonResponse<String> hello() {
        return new JsonResponse<>("000", "", "Hello, world!");
    }
}
