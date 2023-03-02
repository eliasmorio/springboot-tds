package edu.spring.stories.entities

import jakarta.persistence.*

@Entity
class Developer() {

    constructor(firstname: String, lastname: String) : this() {
        this.firstname = firstname
        this.lastname = lastname
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    var firstname: String? = null
    var lastname: String? = null

    @OneToMany(mappedBy = "developer", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var stories: MutableSet<Story> = mutableSetOf()

    fun addStory(story: Story){
        if (stories.contains(story)) return
        stories.add(story)
        story.developer = this
    }

    fun giveUpStory(story: Story){
        if (!stories.contains(story)) return
        stories.remove(story)
        story.developer = null
    }

    @PreRemove
    fun preRemove(){
        stories.forEach { it.developer = null }
    }

}