package edu.spring.td2.controllers

import edu.spring.td2.entities.Group
import edu.spring.td2.entities.User
import edu.spring.td2.repositories.GroupRepository
import edu.spring.td2.services.UIMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
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

    @GetMapping("/edit/{id}")
    fun edit(model: ModelMap,
             @PathVariable id: Int) : String{
        model["group"] = groupRepository.findById(id).get()
        if(model["group"] == null)
            return "redirect:/groups" //TODO : display error
        return "groups/edit"
    }

    @GetMapping("/display/{id}")
    fun display(model: ModelMap, @PathVariable id: Int,
                attrs: RedirectAttributes
    ) : String{
        val group =  groupRepository.findById(id)
        if (!group.isPresent) {
            addMsg(false, attrs, "Erreur", "", "Ce groupe n'existe pas")
            return "redirect:/groups"
        }
        model["group"] = group.get()
        return "groups/display"
    }

    @GetMapping("/delete/{id}")
    fun delete(model: ModelMap, @PathVariable id: Int, attrs: RedirectAttributes) : String{
        val group = groupRepository.findById(id).get()
        val msg = UIMessage.message("Confirmation de suppression",
            "Confirmez-vous la suppression de '<em>${group.name}</em>' ?",
            "red",
            "question circle",
            "/groups/delete/$id",
            "/groups")
        attrs.addFlashAttribute("message", msg)
        return "redirect:/groups"
    }

    @PostMapping("/delete/{id}")
    fun destroy(@PathVariable id: Int) : String{
        val group = groupRepository.findById(id).get()
        groupRepository.delete(group)
        return "redirect:/groups"
    }

}