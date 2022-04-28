package com.key.oa.service;

import com.key.oa.entity.Role;

import java.util.List;

/**
 * 角色类相关的功能
 *
 * @author 孙强
 */
public interface RoleService {
    /**
     * 返回所有的角色优先级表达式
     *
     * @return 角色优先级的表达式，ROLE_A > ROLE_B \n ROLE_B > ROLE_C
     */
    String getRoleHierarchyExpression();
}
