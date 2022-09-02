package com.key.oa.service;

import com.key.oa.common.JsonResponse;
import com.key.oa.dto.LoginDTO;

/**
 * @author 孙强
 * 用于登录的Service
 */
public interface LoginService {
    /**
     * 登录的具体方法
     *
     * @param loginDTO 登录的必要信息：工号和密码
     * @return 登录结果，直接由Controller返回给前端
     */
    JsonResponse<String> login(LoginDTO loginDTO);

    /**
     * 退出登录的具体方法
     *
     * @return 成功退出登录
     */
    JsonResponse<Void> logout();
}
