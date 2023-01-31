package edu.spring.td2.controllers

import edu.spring.td2.entities.Organization
import edu.spring.td2.repositories.OrganizationRepository
import edu.spring.td2.services.UIMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
@RequestMapping("/orgas")
class OrganizationController {

    @Autowired
    lateinit var organiaztionRepository: OrganizationRepository

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
        model["orgas"] = organiaztionRepository.findAll()
        return "orgas/index"
    }

    @GetMapping("/new")
    fun new(model: ModelMap) : String{
        model["orga"] = Organization()
        return "orgas/new"
    }

    @PostMapping("/store")
    fun store(@ModelAttribute orga:Organization?) : String{
        if (orga != null) {
            organiaztionRepository.saveAndFlush(orga)
        }
        return "redirect:/orgas"
    }

    @GetMapping("/edit/{id}")
    fun edit(model: ModelMap,
             @PathVariable id: Int) : String{
        model["orga"] = organiaztionRepository.findById(id).get()
        if(model["orga"] == null)
            return "redirect:/orgas" //TODO : display error
        return "orgas/edit"
    }

    @GetMapping("/delete/{id}")
    fun delete(model: ModelMap, @PathVariable id: Int) : String{
        val org = organiaztionRepository.findById(id).get()

        model["message"] = UIMessage.message("Confirmation de suppression",
            "Confirmez-vous la suppression de '<em>${org.name}</em>' ?",
            "red",
            "question circle",
            "/orgas/delete/$id",
            "/orgas")

        model["orgas"] = organiaztionRepository.findAll()
        return "/orgas/index"
    }

    @PostMapping("/delete/{id}")
    fun destroy(@PathVariable id: Int) : String{
        val org = organiaztionRepository.findById(id).get()
        organiaztionRepository.delete(org)
        return "redirect:/orgas"
    }

    @GetMapping("/display/{id}")
    fun display(model: ModelMap, @PathVariable id: Int,
                attrs: RedirectAttributes) : String{
        val org =  organiaztionRepository.findById(id)
        if (!org.isPresent) {
            addMsg(false, attrs, "Erreur", "", "Cette organisation n'existe pas")
            return "redirect:/orgas"
        }
        model["orga"] = org.get()
        return "orgas/display"
    }



}