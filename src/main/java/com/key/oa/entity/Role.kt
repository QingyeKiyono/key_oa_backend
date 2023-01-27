package com.key.oa.entity

import jakarta.persistence.*
import lombok.ToString

@Entity
class Role(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,

    @Column(nullable = false, unique = true) var name: String,

    @Column(nullable = false) var active: Boolean,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER) var parent: Role?,

    @ToString.Exclude
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "parent")
    var children: MutableSet<Role> = HashSet(),

    @ToString.Exclude
    @ManyToMany var employees: MutableSet<Employee> = HashSet(),

    @ToString.Exclude
    @ManyToMany var permissions: MutableSet<Permission> = HashSet(),

    @ToString.Exclude
    @ManyToMany var pageRes: MutableSet<PageRes> = HashSet(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Role) return false

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}