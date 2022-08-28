package com.key.oa.service;

import com.key.oa.entity.PageRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * @author 孙强
 */
public interface PageResService {
    /**
     * 查询用户可以访问的页面
     *
     * @return 当前登录用户能访问的页面列表
     */
    Set<PageRes> getPageResOfCurrentUser();

    /**
     * 获取全部的页面资源信息
     *
     * @param pageable 分页信息
     * @return 当前页面的页面资源信息
     */
    Page<PageRes> findAll(Pageable pageable);
}
