package oa.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class PageRes(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?,

    @Column(nullable = false, unique = true) var url: String,

    @Column(nullable = false) var description: String,

    @Column(nullable = false) var icon: String,

    @Column(nullable = false) var pageGroup: Boolean,  // Whether this is a page group.

    @JsonIgnore
    @ManyToMany(mappedBy = "pageRes", cascade = [CascadeType.ALL]) var roles: MutableSet<Role> = HashSet(),

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var parent: PageRes?,

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "parent")
    var children: MutableSet<PageRes> = HashSet(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PageRes) return false

        if (id != other.id) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + url.hashCode()
        return result
    }
}