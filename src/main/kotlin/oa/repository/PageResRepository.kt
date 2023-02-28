package oa.repository

import oa.entity.PageRes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface PageResRepository : JpaRepository<PageRes, Long>, JpaSpecificationExecutor<PageRes> {
    /**
     * 根据Url查找页面信息
     *
     * @param urls 需要查找的页面url
     * @return 找到的页面信息
     */
    fun findAllByUrlIn(urls: List<String>): List<PageRes>
}
