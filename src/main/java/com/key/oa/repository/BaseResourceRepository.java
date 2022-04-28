package com.key.oa.repository;

import com.key.oa.entity.BaseResource;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 孙强
 * 资源类的Repository
 */
@Repository
public interface BaseResourceRepository extends JpaRepository<BaseResource, Long>, JpaSpecificationExecutor<BaseResource> {
}