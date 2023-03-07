package edu.spring.stories.repositories

import edu.spring.stories.entities.Developer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "developers", path = "developers")
interface RestDeveloperRepository : JpaRepository<Developer, Int> {
}