package edu.spring.stories.entities

import jakarta.persistence.*

@Entity
class Tag(){

    constructor(color: String, label: String) : this() {
        this.label = label
        this.color = color
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null
    var label: String? = null
    var color: String? = null
    @ManyToMany(mappedBy = "tags")
    var stories: MutableSet<Story> = mutableSetOf()
}