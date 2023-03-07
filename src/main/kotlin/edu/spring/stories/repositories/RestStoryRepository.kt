package edu.spring.stories.repositories

import edu.spring.stories.entities.Story
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "stories", path = "stories")
interface RestStoryRepository : JpaRepository<Story, Int> {
}