package com.key.oa.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Permission(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,

    @Column(nullable = false, unique = true) var value: String,

    @Column(nullable = false) var description: String,

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions") var roles: MutableSet<Role> = HashSet(),

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var parent: Permission?,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "parent")
    var children: MutableSet<Permission> = HashSet(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Permission) return false

        if (id != other.id) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}