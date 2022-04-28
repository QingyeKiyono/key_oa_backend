package com.key.oa.service.impl;

import com.key.oa.entity.Role;
import com.key.oa.repository.RoleRepository;
import com.key.oa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RoleService的实现类
 *
 * @author 孙强
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public String getRoleHierarchyExpression() {
        List<Role> activeRoles = roleRepository.getAllByActive(true);

        return activeRoles.stream()
                .map(role -> role.getName() + " > " + role.getParent().getName())
                .collect(Collectors.joining("\n"));
    }
}
