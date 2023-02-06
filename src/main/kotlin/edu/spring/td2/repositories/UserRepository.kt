package edu.spring.td2.repositories

import edu.spring.td2.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
}