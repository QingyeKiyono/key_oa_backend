package com.key.oa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.key.oa.annotation.Desensitize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 孙强
 * 员工实体类
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Desensitize(strategy = Desensitize.DesensitizeStrategy.PHONE)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    @Desensitize(strategy = Desensitize.DesensitizeStrategy.ID_CARD)
    private String identity;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date birthday;

    /**
     * 员工的工号
     */
    @Column(nullable = false, unique = true)
    private String jobNumber;

    @Column(nullable = false)
    @Desensitize(strategy = Desensitize.DesensitizeStrategy.PASSWORD)
    private String password;

    @Column(nullable = false)
    private Boolean verified;

    @JsonIgnore
    @ManyToMany(mappedBy = "employees")
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee employee)) {
            return false;
        }
        return Objects.equal(getId(), employee.getId()) && Objects.equal(getIdentity(), employee.getIdentity()) && Objects.equal(getJobNumber(), employee.getJobNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getIdentity(), getJobNumber());
    }
}
