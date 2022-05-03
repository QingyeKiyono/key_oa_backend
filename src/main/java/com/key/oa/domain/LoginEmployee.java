package com.key.oa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.key.oa.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author 孙强
 * 登录员工的信息
 * 需要忽视掉不知道的field，否则在从redis中取数据时会报错
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginEmployee implements UserDetails {

    private Employee employee;

    private List<String> permissions;

    /**
     * 封装后的权限信息
     * SimpleGrantedAuthority默认情况下无法被序列化，因此需要忽略
     */
    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;

    public LoginEmployee(Employee employee, List<String> permissions) {
        this.employee = employee;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.nonNull(authorities)) {
            return authorities;
        }

        authorities = permissions.stream().map(SimpleGrantedAuthority::new).toList();
        return authorities;
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getJobNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
