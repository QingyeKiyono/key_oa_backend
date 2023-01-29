package com.key.oa.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.key.oa.entity.Employee
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
open class LoginEmployee constructor(val employee: Employee, private val permissions: List<String>) : UserDetails {
    @JsonIgnore
    private var authorities: MutableList<SimpleGrantedAuthority>? = null

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        if (Objects.nonNull(authorities)) {
            return authorities!!
        }

        authorities = permissions.map { SimpleGrantedAuthority(it) }.toMutableList()
        return authorities!!
    }

    override fun getPassword(): String = employee.password

    override fun getUsername(): String = employee.jobNumber

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}