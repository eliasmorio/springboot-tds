package edu.spring.td2.controllers

import edu.spring.td2.entities.Organization
import edu.spring.td2.repositories.OrganizationRepository
import edu.spring.td2.services.OrganizationService
import edu.spring.td2.services.ui.UIMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
@RequestMapping(OrganizationController.mapping)
class OrganizationController {
    companion object {
        const val mapping = "/orgas"
    }

    @Autowired
    lateinit var organiaztionRepository: OrganizationRepository
    @Autowired
    lateinit var organizationService: OrganizationService

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
    fun index(model: ModelMap, attrs: RedirectAttributes) : String{
        model["table"] = organizationService.getUITable()
        return "entityIndex"
    }

    @GetMapping("/display/{id}")
    fun display(model: ModelMap,
                @PathVariable id: Int) : String{
        model["orga"] = organiaztionRepository.findById(id).get()
        if(model["orga"] == null)
            return "redirect:$mapping" //TODO : display error
        model["table"] = organizationService.getUIDisplay(model["orga"] as Organization)
        return "entityDisplay"
    }

    @GetMapping("/new")
    fun new(model: ModelMap) : String{
        model["form"] = organizationService.getUIForm(Organization())
        return "entityForm"
    }

    @PostMapping("/store")
    fun store(@ModelAttribute orga:Organization?) : String{
        organiaztionRepository.save(orga!!)
        return "redirect:$mapping"
    }

    @GetMapping("/edit/{id}")
    fun edit(model: ModelMap,
             @PathVariable id: Int) : String{
        model["orga"] = organiaztionRepository.findById(id).get()
        if(model["orga"] == null)
            return "redirect:$mapping" //TODO : display error
        model["form"] = organizationService.getUIForm(model["orga"] as Organization)
        return "entityForm"
    }

    @GetMapping("/delete/{id}")
    fun delete(model: ModelMap,
               @PathVariable id: Int,
               attrs: RedirectAttributes) : String{
        val org = organiaztionRepository.findById(id).get()
        attrs.addFlashAttribute("message", UIMessage.deleteMessage(org.name, mapping, id))
        return "redirect:/orgas"
    }

    @PostMapping("/delete/{id}")
    fun destroy(@PathVariable id: Int) : String{
        val org = organiaztionRepository.findById(id).get()
        organiaztionRepository.delete(org)
        return "redirect:$mapping"
    }







}