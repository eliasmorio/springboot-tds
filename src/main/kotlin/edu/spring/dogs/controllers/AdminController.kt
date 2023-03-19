package edu.spring.dogs.controllers

import edu.spring.dogs.entities.User
import edu.spring.dogs.repositories.RoleRepository
import edu.spring.dogs.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AdminController {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository


    @GetMapping("")
    fun admin(model : ModelMap, authentication: Authentication) : String{
        model["users"] = userRepository.findAll()
        model["roles"] = roleRepository.findAll()
        model["user"] = authentication

        return "admin"
    }

    @PostMapping("/users")
    fun addUser(@ModelAttribute user: User) : String{
        userRepository.save(user)
        return "redirect:/admin"
    }


}