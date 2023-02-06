package edu.spring.td2.controllers

import edu.spring.td2.entities.User
import edu.spring.td2.repositories.UserRepository
import edu.spring.td2.services.ui.UIMessage
import edu.spring.td2.services.UserService
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
    @Autowired
    lateinit var userService: UserService

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
        model["table"] = userService.getUITable()
        return "entityIndex"
    }

    @GetMapping("/new")
    fun new(model: ModelMap) : String{
        model["form"] = userService.getUIForm(User())
        return "entityForm"
    }

    @GetMapping("/edit/{id}")
    fun edit(model: ModelMap,
             @PathVariable id: Int) : String{
        model["user"] = userRepository.findById(id).get()
        if(model["user"] == null)
            return "redirect:/users" //TODO : display error
        model["form"] = userService.getUIForm(model["user"] as User)
        return "entityForm"
    }

    @PostMapping("/store")
    fun store(@ModelAttribute user: User?) : String{
        if (user != null) {
            userRepository.save(user)
        }
        return "redirect:/users"
    }



    @GetMapping("/display/{id}")
    fun display(model: ModelMap, @PathVariable id: Int,
                attrs: RedirectAttributes) : String{
        val user =  userRepository.findById(id)
        if (!user.isPresent) {
            addMsg(false, attrs, "Erreur", "", "Cette utilisateur n'existe pas")
            return "redirect:/users"
        }
        model["table"] = userService.getUIDetailTable(user.get())
        return "entityDisplay"
    }

    @GetMapping("/delete/{id}")
    fun delete(model: ModelMap, @PathVariable id: Int, attrs: RedirectAttributes) : String{
        val user = userRepository.findById(id).get()
        attrs.addFlashAttribute(UIMessage.deleteMessage("${user.firstname} ${user.lastname}", "/users", id))
        return "redirect:/users"
    }

    @PostMapping("/delete/{id}")
    fun destroy(@PathVariable id: Int) : String{
        val user = userRepository.findById(id).get()
        userRepository.delete(user)
        return "redirect:/users"
    }




}