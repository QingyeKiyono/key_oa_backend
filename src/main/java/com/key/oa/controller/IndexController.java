package com.key.oa.controller;

import com.key.oa.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 孙强
 * 用做测试接口
 */
@RestController
public class IndexController {

    private final JwtUtil jwtUtil;

    @Autowired
    public IndexController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/")
    public String index() {
        return "Hello, world!";
    }

    @GetMapping("/jwt")
    public String generateJwt() {
        return jwtUtil.generate();
    }
}
