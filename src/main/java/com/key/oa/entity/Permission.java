package com.key.oa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
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

    @ManyToMany(mappedBy = "permissions")
    @ToString.Exclude
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    /**
     * 这里的父子关系要求必须得删除掉所有的子角色后才能删除父角色（可以同时删除）
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Permission parent;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<Permission> children = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Permission that)) {
            return false;
        }
        return Objects.equal(id, that.id) && Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, value);
    }
}
