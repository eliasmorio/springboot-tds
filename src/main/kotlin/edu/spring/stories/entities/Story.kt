package edu.spring.stories.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Story {

    @Id
    var id: Int? = null
    var name: String? = null
    @ManyToOne
    var developer: Developer? = null
    @OneToMany
    var tags: MutableSet<Tag> = mutableSetOf()

}