package edu.spring.td2.controllers

import edu.spring.td2.entities.User
import edu.spring.td2.repositories.UserRepository
import edu.spring.td2.services.UIMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    private fun addMsg(resp:Boolean, attrs: RedirectAttributes, title:String, success:String, error:String){
        if(resp) {
            attrs.addFlashAttribute("msg",
                UIMessage.message(title, success))
        } else {
            attrs.addFlashAttribute("msg",
                UIMessage.message(title, error,"error","warning circle"))
        }
    }

    @GetMapping("", "/")
    fun index(model: ModelMap) : String {
        model["users"] = userRepository.findAll()
        for (user in userRepository.findAll()){
            println(user)
        }
        return "users/index"
    }

    @GetMapping("/new")
    fun new(model: ModelMap) : String{
        model["user"] = User()
        return "users/new"
    }

    @PostMapping("/store")
    fun store(@ModelAttribute user: User?) : String{
        println(user)
        if (user != null) {
            userRepository.saveAndFlush(user)
        }
        return "redirect:/users"
    }

    @GetMapping("/edit/{id}")
    fun edit(model: ModelMap,
             @PathVariable id: Int) : String{
        model["user"] = userRepository.findById(id).get()
        if(model["user"] == null)
            return "redirect:/users" //TODO : display error
        return "users/edit"
    }

    @GetMapping("/display/{id}")
    fun display(model: ModelMap, @PathVariable id: Int,
                attrs: RedirectAttributes
    ) : String{
        val user =  userRepository.findById(id)
        if (!user.isPresent) {
            addMsg(false, attrs, "Erreur", "", "Cette utilisateur n'existe pas")
            return "redirect:/users"
        }
        model["user"] = user.get()
        return "users/display"
    }

    @GetMapping("/delete/{id}")
    fun delete(model: ModelMap, @PathVariable id: Int, attrs: RedirectAttributes) : String{
        val user = userRepository.findById(id).get()

        val msg = UIMessage.message("Confirmation de suppression",
            "Confirmez-vous la suppression de '<em>${user.firstname} ${user.lastname} </em>' ?",
            "red",
            "question circle",
            "/users/delete/$id",
            "/users")
        attrs.addFlashAttribute("message", msg)
        return "redirect:/users"
    }

    @PostMapping("/delete/{id}")
    fun destroy(@PathVariable id: Int) : String{
        val user = userRepository.findById(id).get()
        userRepository.delete(user)
        return "redirect:/users"
    }




}