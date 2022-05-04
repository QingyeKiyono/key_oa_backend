package com.key.oa.controller.dev;

import com.key.oa.common.JsonResponse;
import com.key.oa.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 孙强
 * 用于测试JwtUtil的功能
 */
@RestController
@RequestMapping("/test/jwt")
public class JwtUtilController {
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtUtilController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/generate")
    @PreAuthorize("hasAuthority('dev:test')")
    public JsonResponse<Object> generate(@RequestParam String subject) {
        return new JsonResponse<>(jwtUtil.generate(subject));
    }
}
