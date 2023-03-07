package edu.spring.stories.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.springframework.data.rest.core.annotation.RestResource

@Entity
class Story() {

    constructor(name: String) : this() {
        this.name = name
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    var name: String? = null
    @ManyToOne(optional = true)
    @RestResource(exported = false, rel = "developer", path = "developer")
    @JsonBackReference
    var developer: Developer? = null
    @ManyToMany
    var tags: MutableSet<Tag> = mutableSetOf()

}