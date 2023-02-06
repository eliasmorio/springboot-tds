package edu.spring.td2.controllers

import edu.spring.td2.entities.Group
import edu.spring.td2.repositories.GroupRepository
import edu.spring.td2.services.GroupService
import edu.spring.td2.services.ui.UIMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping("/groups")
class GroupController {

    @Autowired
    lateinit var groupRepository: GroupRepository
    @Autowired
    lateinit var groupService: GroupService

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
        model["table"] = groupService.getUITable()
        return "entity/index"
    }

    @GetMapping("/new")
    fun new(model: ModelMap) : String{
        model["form"] = groupService.getUIForm(Group())
        return "entity/form"
    }

    @GetMapping("/edit/{id}")
    fun edit(model: ModelMap,
             @PathVariable id: Int) : String{
        model["group"] = groupRepository.findById(id).get()
        if(model["group"] == null)
            return "redirect:/groups" //TODO : display error
        model["form"] = groupService.getUIForm(model["group"] as Group)
        return "entity/form"
    }

    @PostMapping("/store")
    fun store(@ModelAttribute group: Group?) : String{
        if (group != null) {
            groupRepository.save(group)
        }
        return "redirect:/groups"
    }

    @GetMapping("/display/{id}")
    fun display(model: ModelMap, @PathVariable id: Int,
                attrs: RedirectAttributes) : String{
        val group =  groupRepository.findById(id)
        if (!group.isPresent) {
            addMsg(false, attrs, "Erreur", "", "Ce groupe n'existe pas")
            return "redirect:/groups"
        }
        model["table"] = groupService.getUIDisplay(group.get())
        return "entityDisplay"
    }

    @GetMapping("/delete/{id}")
    fun delete(model: ModelMap, @PathVariable id: Int, attrs: RedirectAttributes) : String{
        val group = groupRepository.findById(id).get()
        attrs.addFlashAttribute(UIMessage.deleteMessage(group.name?:"", "/groups", id))
        return "redirect:/groups"
    }

    @PostMapping("/delete/{id}")
    fun destroy(@PathVariable id: Int) : String{
        val group = groupRepository.findById(id).get()
        groupRepository.delete(group)
        return "redirect:/groups"
    }

}