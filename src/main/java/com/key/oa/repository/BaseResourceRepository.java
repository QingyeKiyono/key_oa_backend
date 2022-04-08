package com.key.oa.repository;

import com.key.oa.entity.BaseResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author 孙强
 * 资源类的Repository
 */
@Repository
public interface BaseResourceRepository extends JpaRepository<BaseResource, Long>, JpaSpecificationExecutor<BaseResource> {
}