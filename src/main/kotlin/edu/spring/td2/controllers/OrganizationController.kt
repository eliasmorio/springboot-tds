package edu.spring.td2.controllers

import edu.spring.td2.entities.Organization
import edu.spring.td2.repositories.OrganizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/orgas")
class OrganizationController {

    @Autowired
    lateinit var organiaztionRepository: OrganizationRepository

    @GetMapping("", "/")
    fun index(model: ModelMap) : String{
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



}