package edu.spring.td2.controllers

import edu.spring.td2.entities.Group
import edu.spring.td2.entities.User
import edu.spring.td2.repositories.GroupRepository
import edu.spring.td2.services.UIMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/groups")
class GroupController {

    @Autowired
    lateinit var groupRepository: GroupRepository

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
        model["groups"] = groupRepository.findAll()
        return "groups/index"
    }

    @GetMapping("/new")
    fun new(model: ModelMap) : String{
        model["group"] = User()
        return "groups/new"
    }

    @PostMapping("/store")
    fun store(@ModelAttribute group: Group?) : String{
        if (group != null) {
            groupRepository.save(group)
        }
        return "redirect:/groups"
    }

}