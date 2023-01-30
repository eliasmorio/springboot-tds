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
    open var groups: HashSet<Group> = hashSetOf()

    @OneToMany
    open var users: HashSet<User> = hashSetOf()




}
