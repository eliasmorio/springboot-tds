package edu.spring.dogs.repositories

import edu.spring.dogs.entities.Role
import org.springframework.data.repository.CrudRepository

interface RoleRepository : CrudRepository<Role, Int> {

    fun findRoleByName(name:String) : Role


}