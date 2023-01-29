package com.key.oa.service

import com.key.oa.entity.PageRes
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PageResService {
    /**
     * 查询用户可以访问的页面
     *
     * @return 当前登录用户能访问的页面列表
     */
    fun getPageResOfCurrentUser(): Set<PageRes>

    /**
     * 获取全部的页面资源信息
     *
     * @param pageable 分页信息
     * @return 当前页面的页面资源信息
     */
    fun findAll(pageable: Pageable): Page<PageRes>
}