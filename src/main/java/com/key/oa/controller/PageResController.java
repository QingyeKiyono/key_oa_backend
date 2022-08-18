package com.key.oa.controller;

import com.key.oa.common.JsonResponse;
import com.key.oa.entity.PageRes;
import com.key.oa.service.PageResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author 孙强
 */
@RestController
@RequestMapping("/pages")
public class PageResController {
    private final PageResService pageResService;

    @Autowired
    public PageResController(PageResService pageResService) {
        this.pageResService = pageResService;
    }

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public JsonResponse<Set<PageRes>> getPagesOfCurrentUser() {
        return new JsonResponse<>(pageResService.getPageResOfCurrentUser());
    }
}
