package com.key.oa.controller;

import com.key.oa.common.JsonResponse;
import com.key.oa.entity.Page;
import com.key.oa.service.PageService;
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
public class PageController {
    private final PageService pageService;

    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public JsonResponse<Set<Page>> getPagesOfCurrentUser() {
        return new JsonResponse<>(pageService.getPagesOfCurrentUser());
    }
}
