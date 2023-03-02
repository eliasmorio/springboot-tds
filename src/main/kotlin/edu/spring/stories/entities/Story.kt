package edu.spring.stories.entities

import jakarta.persistence.*

@Entity
class Story() {

    constructor(name: String) : this() {
        this.name = name
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null
    var name: String? = null
    @ManyToOne
    var developer: Developer? = null
    @ManyToMany
    var tags: MutableSet<Tag> = mutableSetOf()

}