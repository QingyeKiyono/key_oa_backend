package com.key.oa.repository;

import com.key.oa.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 孙强
 * 资源类的Repository
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {
    /**
     * 查找所有的页面资源
     *
     * @return 所有的页面资源
     */
    List<Resource> getResourceByPageIsTrue();
}