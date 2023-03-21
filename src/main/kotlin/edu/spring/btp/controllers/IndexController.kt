package edu.spring.btp.controllers

import edu.spring.btp.repositories.DomainRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class IndexController {

    @Autowired
    lateinit var domainRepository: DomainRepository



    @RequestMapping(path = ["/", "/index",])
    fun index(model: ModelMap): String {
        val root = domainRepository.findByName("Root")
        model["root"] = root
        print(root.name)
        model["domains"] = domainRepository.findByParentId(root.id)
        return "index"
    }
}