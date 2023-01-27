package com.key.oa.repository

import com.key.oa.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
}