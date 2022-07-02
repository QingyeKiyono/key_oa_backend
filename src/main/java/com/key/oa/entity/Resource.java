package com.key.oa.entity;

import com.google.common.base.Objects;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 孙强
 * 资源的基类
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "page"})})
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源的名称，便于用户理解
     */
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String value;

    /**
     * 只有页面资源拥有的属性
     * 对应页面项展示的图标，例如：'mdi-folder'
     */
    @Column
    private String icon;

    /**
     * 是不是页面资源，即浏览器的url
     */
    @Column(nullable = false)
    private Boolean page;

    @ManyToMany(mappedBy = "resources")
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    /**
     * 这里的父子关系要求必须得删除掉所有的子角色后才能删除父角色（可以同时删除）
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Resource parent;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<Resource> children = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource that)) {
            return false;
        }
        return Objects.equal(id, that.id) && Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, value);
    }
}
