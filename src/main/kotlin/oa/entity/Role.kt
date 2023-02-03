package oa.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Role(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,

    @Column(nullable = false, unique = true) var name: String,

    @Column(nullable = false) var active: Boolean,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER) var parent: Role?,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "parent")
    var children: MutableSet<Role> = HashSet(),

    @JsonIgnore
    @ManyToMany var employees: MutableSet<Employee> = HashSet(),

    @JsonIgnore
    @ManyToMany var permissions: MutableSet<Permission> = HashSet(),

    @JsonIgnore
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