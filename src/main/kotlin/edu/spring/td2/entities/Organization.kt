package edu.spring.td2.entities

import jakarta.persistence.*

@Entity
@Table(name = "TD2_ORGANIZATION")
open class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int? = null

    @Column(length = 60)
    open  var name : String? = null
    @Column(nullable = false, length = 45)
    open var domain: String? = null
    @Column(nullable = false, length = 45)
    open var aliases: String? = null


    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "organization")
    open var groups: MutableSet<Group> = mutableSetOf()

    @OneToMany(mappedBy = "organization")
    open var users: MutableSet<User> = mutableSetOf()

    override fun toString(): String {
        return "Organization(id=$id, name='$name', domain=$domain, aliases=$aliases, groups=$groups, users=$users)"
    }



}
