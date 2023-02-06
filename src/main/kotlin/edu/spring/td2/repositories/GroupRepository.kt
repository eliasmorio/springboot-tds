package edu.spring.td2.repositories

import edu.spring.td2.entities.Group
import org.springframework.data.repository.CrudRepository

interface GroupRepository : CrudRepository<Group, Int> {

    abstract fun findByNameContainingOrEmailContainingOrAliasesContaining(query: String, query1: String, query2: String): List<Group>?
}