package edu.spring.dogs.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Role() {

    constructor(name: String):this(){
        this.name = name
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int=0

    lateinit var name:String

    @OneToMany(mappedBy = "role")
    val users = mutableSetOf<User>()
}