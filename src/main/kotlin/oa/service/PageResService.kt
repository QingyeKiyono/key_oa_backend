package oa.service

import oa.entity.PageRes
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
     * @return 全部的页面资源
     */
    fun findAll(): List<PageRes>

    /**
     * 获取全部的页面资源信息，使用分页查询
     *
     * @param pageable 分页信息
     * @return 当前页面的页面资源信息
     */
    fun findAll(pageable: Pageable): List<PageRes>

    /**
     * 查询页面数量
     *
     * @return 页面的数量
     */
    fun count(): Long
}