package com.key.oa.repository

import com.key.oa.entity.Permission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository : JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
}