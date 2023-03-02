package edu.spring.stories.repositories

import edu.spring.stories.entities.Developer
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface DeveloperRepository : CrudRepository<Developer, Int> {

    @Query("SELECT d FROM Developer d where d.id in (SELECT s.developer.id FROM Story s WHERE s.name = :name)")
    fun findDeveloperByStoriesName(name: String): List<Developer>?

    fun findByFirstnameAndLastname(firstname: String, lastname: String): List<Developer>?
}