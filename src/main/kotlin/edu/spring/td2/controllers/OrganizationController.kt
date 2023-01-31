package edu.spring.td2.controllers

import edu.spring.td2.entities.Organization
import edu.spring.td2.repositories.OrganizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.Mapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/orgas")
class OrganizationController {

    @Autowired
    lateinit var organiaztionRepository: OrganizationRepository

    @RequestMapping("", "/")
    fun index(model: ModelMap) : String{
        model["orgas"] = organiaztionRepository.findAll()
        return "orgas/index"
    }

}