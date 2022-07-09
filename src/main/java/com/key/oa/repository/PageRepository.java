package com.key.oa.repository;

import com.key.oa.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 孙强
 */
public interface PageRepository extends JpaRepository<Page, Long>, JpaSpecificationExecutor<Page> {
}