package edu.spring.td2.repositories

import edu.spring.td2.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {
}