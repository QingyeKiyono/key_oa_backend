package com.key.oa.repository

import com.key.oa.entity.PageRes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface PageResRepository : JpaRepository<PageRes, Long>, JpaSpecificationExecutor<PageRes>