package edu.spring.td2.entities

import jakarta.persistence.*

@Entity
@Table(name = "TD2_ORGANIZATION")
open class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Int? = null

    open lateinit var name: String
    open var domain: String? = null
    open var aliases: String? = null


    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "organization")
    open var groups: MutableSet<Group> = mutableSetOf()

    @OneToMany
    open var users: MutableSet<User> = mutableSetOf()

    override fun toString(): String {
        return "Organization(id=$id, name='$name', domain=$domain, aliases=$aliases, groups=$groups, users=$users)"
    }



}
