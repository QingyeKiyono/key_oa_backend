package oa.controller

import oa.common.JsonResponse
import oa.common.JsonResponse.Companion.success
import oa.entity.PageRes
import oa.service.PageResService
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
    fun getForCurrentUser(): JsonResponse<Set<PageRes>> =
        success(pageResService.getPageResOfCurrentUser())

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    fun getList(@RequestParam page: Int, @RequestParam size: Int)
            : JsonResponse<List<PageRes>>? {
        require(page >= 1)

        val pages = if (size < 0) {
            // 返回全部页面信息
            pageResService.findAll()
        } else {
            // 返回特定分页的页面信息
            pageResService.findAll(PageRequest.of(page - 1, size))
        }
        return success(pages)
    }

    @GetMapping("/count")
    @PreAuthorize("isAuthenticated()")
    fun count(): JsonResponse<Long> = success(pageResService.count())
}