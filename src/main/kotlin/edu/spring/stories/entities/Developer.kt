package edu.spring.stories.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Developer {

    @Id
    var id: Long? = null
    var firstname: String? = null
    var lastname: String? = null

    @OneToMany(mappedBy = "developer")
    var stories: MutableSet<Story> = mutableSetOf()


}