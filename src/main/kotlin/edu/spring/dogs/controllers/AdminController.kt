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
import org.springframework.web.bind.annotation.PathVariable
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
    fun admin(model : ModelMap, auth: Authentication) : String{
        model["users"] = userRepository.findAll()
        model["roles"] = roleRepository.findAll()
        model["user"] = auth
        return "admin"
    }

    @PostMapping("/users")
    fun addUser(@ModelAttribute user: User) : String{
        userRepository.save(user)
        return "redirect:/admin"
    }

    @GetMapping("/users/{id}/delete")
    fun deleteUser(@PathVariable id:Int) : String {
        userRepository.deleteById(id)
        return "redirect:/admin"
    }

    @GetMapping("/users/{id}/edit")
    fun editUser(@PathVariable id:Int, model: ModelMap, auth: Authentication) : String {
        model["users"] = userRepository.findAll()
        model["roles"] = roleRepository.findAll()
        model["user"] = auth
        model["editUser"] = userRepository.findById(id).get()
        return "admin"
    }

}