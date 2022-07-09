package com.key.oa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 页面资源对应的实体类
 * @author 孙强
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String icon;

    /**
     * 表明是否是一个页面导航组的内容
     * 如果是，那么url属性不使用，只使用其children属性
     * 如果不是，那么其children属性不使用，只使用其URL属性
     */
    @Column(nullable = false)
    private Boolean pageGroup;

    @ManyToMany(mappedBy = "pages")
    @ToString.Exclude
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Page parent;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<Page> children = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Page page)) {
            return false;
        }
        return Objects.equal(getId(), page.getId()) && Objects.equal(getUrl(), page.getUrl()) && Objects.equal(getDescription(), page.getDescription()) && Objects.equal(getIcon(), page.getIcon()) && Objects.equal(getParent(), page.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getUrl(), getDescription(), getIcon(), getParent());
    }
}
