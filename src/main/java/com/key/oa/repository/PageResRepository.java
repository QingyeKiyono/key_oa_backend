package com.key.oa.repository;

import com.key.oa.entity.PageRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author 孙强
 */
@Repository
public interface PageResRepository extends JpaRepository<PageRes, Long>, JpaSpecificationExecutor<PageRes> {
}