package com.key.oa.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.key.oa.annotation.Desensitize
import jakarta.persistence.*
import java.util.*
import kotlin.collections.HashSet

@Entity
class Employee(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,

    @Column(nullable = false) var name: String,

    @Desensitize(strategy = Desensitize.DesensitizeStrategy.PHONE)
    @Column(nullable = false, unique = true) var phone: String,

    @Column(nullable = false, unique = true) var email: String,

    @Column(nullable = false, unique = true) var identity: String,

    @Column(nullable = false) var birthday: Date,

    @Column(nullable = false, unique = true) var jobNumber: String,  // 员工的工号

    @Desensitize(strategy = Desensitize.DesensitizeStrategy.PASSWORD)
    @Column(nullable = false) var password: String,

    @Column(nullable = false) var verified: Boolean,

    @JsonIgnore
    @ManyToMany(mappedBy = "employees")
    var roles: MutableSet<Role> = HashSet()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Employee) return false

        if (id != other.id) return false
        if (identity != other.identity) return false
        if (jobNumber != other.jobNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + identity.hashCode()
        result = 31 * result + jobNumber.hashCode()
        return result
    }
}