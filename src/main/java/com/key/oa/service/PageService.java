package com.key.oa.service;

import com.key.oa.entity.Page;

import java.util.Set;

/**
 * @author 孙强
 */
public interface PageService {
    /**
     * 查询用户可以访问的页面
     *
     * @return 当前登录用户能访问的页面列表
     */
    Set<Page> getPagesOfCurrentUser();
}
