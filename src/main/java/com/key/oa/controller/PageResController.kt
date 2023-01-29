package com.key.oa.controller

import com.key.oa.common.JsonResponse
import com.key.oa.entity.PageRes
import com.key.oa.service.PageResService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pages")
class PageResController @Autowired constructor(val pageResService: PageResService) {
    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    fun getPagesOfCurrentUser(): JsonResponse<Set<PageRes>> =
        JsonResponse.success(pageResService.getPageResOfCurrentUser())

    @GetMapping("/")
    fun getPageResList(@RequestParam page: Int, @RequestParam size: Int)
            : JsonResponse<List<PageRes>>? {
        val maxSize = 20
        require(size in 1..maxSize && page >= 1)

        val pages = this.pageResService.findAll(PageRequest.of(page - 1, size))
        return JsonResponse.success(pages.toList())
    }
}