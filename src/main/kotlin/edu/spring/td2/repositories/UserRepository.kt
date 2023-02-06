package edu.spring.td2.repositories

import edu.spring.td2.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {
    abstract fun findByFirstnameContainingOrLastnameContainingOrEmailContaining(query: String, query1: String, query2: String): List<User>?
}