package edu.spring.stories.entities

import jakarta.persistence.*

@Entity
class Story {

    @Id
    var id: Int? = null
    var name: String? = null
    @ManyToOne
    var developer: Developer? = null
    @ManyToMany
    var tags: MutableSet<Tag> = mutableSetOf()

}