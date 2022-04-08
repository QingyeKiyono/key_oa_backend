package com.key.oa.repository;

import com.key.oa.entity.PageResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author 孙强
 * 页面资源的Repository
 */
@Repository
public interface PageResourceRepository extends JpaRepository<PageResource, Long>, JpaSpecificationExecutor<PageResource> {
}