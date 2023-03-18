package edu.spring.dogs.controllers

import edu.spring.dogs.entities.Role
import edu.spring.dogs.entities.User
import edu.spring.dogs.repositories.RoleRepository
import edu.spring.dogs.repositories.UserRepository
import edu.spring.dogs.services.DbUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class InitController {
    @Autowired
    lateinit var dbUserService: UserDetailsService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository


    @RequestMapping("/init/{username}")
    fun createUser(@PathVariable username:String):String {
        val user= User()
        user.username=username
        user.email=username.lowercase()+"@gmail.com"
        user.role=roleRepository.findRoleByName("ADMIN")
        user.password="1234"
        (dbUserService as DbUserService).encodePassword(user)
        userRepository.save(user)
        return "redirect:/"
    }

    @RequestMapping("/init/roles")
    fun createRoles(){
        roleRepository.save(Role("USER"))
        roleRepository.save(Role("MANAGER_DOG"))
        roleRepository.save(Role("MANAGER_MASTER"))
        roleRepository.save(Role("ADMIN"))
    }
}