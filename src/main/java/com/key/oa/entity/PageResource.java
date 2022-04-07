package com.key.oa.entity;

import com.google.common.base.Objects;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author 孙强
 * 页面资源的实体类
 */
@Entity
@ToString(callSuper = true)
public class PageResource extends BaseResource {
    @Column(nullable = false)
    private String description;

    /**
     * 同一级别下菜单的优先级，数字越小优先级越高
     */
    @Column(nullable = false)
    private Integer level = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageResource that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equal(description, that.description) && Objects.equal(level, that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), description, level);
    }
}
