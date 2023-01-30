package edu.spring.td2.entities

import jakarta.persistence.*

@Entity
@Table(name = "TD2_USER")
open class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int? = null
    open var firstname: String? = null
    open var lastname: String? = null
    open var email: String? = null
    open var password: String? = null
    open var suspended: Boolean = false

    @ManyToOne
    open var organization: Organization? = null

    @ManyToMany(mappedBy = "users")
    open var groups: HashSet<Group> = hashSetOf()

}