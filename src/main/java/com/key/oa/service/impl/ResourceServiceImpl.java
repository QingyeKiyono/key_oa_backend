package com.key.oa.service.impl;

import com.key.oa.domain.LoginEmployee;
import com.key.oa.entity.Permission;
import com.key.oa.repository.PermissionRepository;
import com.key.oa.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 相关功能的实现
 * @author 孙强
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    private final PermissionRepository repository;

    @Autowired
    public ResourceServiceImpl(PermissionRepository permissionRepository) {
        repository = permissionRepository;
    }
}
