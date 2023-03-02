package edu.spring.stories.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Tag {

    @Id
    var id: Int? = null
    var name: String? = null
}