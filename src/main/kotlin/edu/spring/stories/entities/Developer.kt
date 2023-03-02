package edu.spring.stories.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.PreRemove

@Entity
class Developer {

    @Id
    var id: Long? = null
    var firstname: String? = null
    var lastname: String? = null

    @OneToMany(mappedBy = "developer")
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