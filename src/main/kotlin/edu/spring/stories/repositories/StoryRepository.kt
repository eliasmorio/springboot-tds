package edu.spring.stories.repositories

import edu.spring.stories.entities.Story
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StoryRepository  : CrudRepository<Story, Int> {

    @Query("SELECT s FROM Story s WHERE s.developer IS NULL")
    fun findStoriesToAssign(): List<Story>?

    fun findByDeveloper_IdAndName(id: Int, name: String): List<Story>?

}