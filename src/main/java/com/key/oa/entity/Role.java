package com.key.oa.entity;

import com.google.common.base.Objects;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 孙强
 * 员工角色类
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean active;

    /**
     * 这里的父子关系要求必须得删除掉所有的子角色后才能删除父角色（可以同时删除）
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Role parent;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<Role> children = new HashSet<>();

    @ManyToMany
    @ToString.Exclude
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @ToString.Exclude
    private Set<Resource> resources = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role role)) {
            return false;
        }
        return Objects.equal(getId(), role.getId()) && Objects.equal(getName(), role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getName());
    }
}
