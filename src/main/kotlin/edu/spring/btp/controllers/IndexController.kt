package edu.spring.btp.controllers

import edu.spring.btp.repositories.DomainRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class IndexController {

    @Autowired
    lateinit var domainRepository: DomainRepository



    @RequestMapping(path = ["/", "/index",])
    fun index(model: ModelMap): String {
        model["domain"] = domainRepository.findByName("Root")
        return "index"
    }

    @GetMapping("/domain/{name}")
    fun domain(@PathVariable name: String, model: ModelMap): String {
        val domain = domainRepository.findByName(name)
        model["back"] = "domain/${domain.parent?.name}"
        model["domain"] = domain
        print(domain.children)
        return "index"
    }

    @GetMapping("/complaints/{domain}")
    fun complaints(@PathVariable domain: String, model: ModelMap): String {
        val domain = domainRepository.findByName(domain)
        model["domain"] = domain
        model["complaints"] = domain.complaints
        return "complaints"
    }





}