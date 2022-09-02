package com.key.oa.repository;

import com.key.oa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author 孙强
 * 角色类的Repository
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    /**
     * 根据是否激活查找角色
     *
     * @param active 角色是否激活
     * @return 找到的角色
     */
    List<Role> getAllByActive(Boolean active);
}