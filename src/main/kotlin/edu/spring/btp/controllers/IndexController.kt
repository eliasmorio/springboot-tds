package edu.spring.btp.controllers

import edu.spring.btp.entities.Complaint
import edu.spring.btp.entities.User
import edu.spring.btp.repositories.ComplaintRepository
import edu.spring.btp.repositories.DomainRepository
import edu.spring.btp.repositories.ProviderRepository
import edu.spring.btp.repositories.UserRepository
import edu.spring.btp.service.DbUserService
import edu.spring.dogs.services.ui.UIMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class IndexController {

    @Autowired
    lateinit var domainRepository: DomainRepository

    @Autowired
    lateinit var complaintRepository: ComplaintRepository

    @Autowired
    lateinit var providerRepository: ProviderRepository

    @Autowired
    lateinit var userRepository : UserRepository

    @Autowired
    lateinit var userDetailsService: UserDetailsService



    @ModelAttribute("auth")
    fun auth(auth: Authentication?) = auth?.principal

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

    @GetMapping("/complaints/{domainName}")
    fun complaints(@PathVariable domainName: String, model: ModelMap): String {
        if (domainName == "Root") return "redirect:/complaints"
        val domain = domainRepository.findByName(domainName)
        model["domain"] = domain
        return "complaints"
    }

    @GetMapping("/complaints/{domainName}/new")
    fun newComplaint(@PathVariable domainName: String, model: ModelMap): String {
        val domain = domainRepository.findByName(domainName)
        model["domain"] = domain
        print(domain.providers)
        return "forms/complaint"
    }

    @PostMapping("/complaints/{domainName}/new", consumes = ["application/x-www-form-urlencoded"])
    fun storeComplaint(@PathVariable domainName: String,
                       @RequestParam title : String,
                       @RequestParam description : String,
                       @RequestParam provider : Int): String {
        val domain = domainRepository.findByName(domainName)
        val provider = providerRepository.findById(provider).get()
        val complaint = Complaint(title, description, null, provider, domain)
        complaintRepository.save(complaint)
        return "redirect:/complaints/$domainName"
    }


    @GetMapping("/signup")
    fun signup(model: ModelMap): String {
        return "forms/signupForm"
    }

    @PostMapping("/signup")
    fun storeUser(@RequestParam username : String,
                  @RequestParam password : String,
                  @RequestParam confPassword : String,
                  @RequestParam email : String) : String {
        if (password != confPassword) return "redirect:/signup"
        val user = User(username)
        user.password = (userDetailsService as DbUserService).passwordEncoder.encode(password)
        user.email = email
        user.role = "USER"
        userRepository.save(user)
        userDetailsService.loadUserByUsername(username)
        return "redirect:/login"
    }


    @GetMapping("/403")
    fun error403(attrs : RedirectAttributes): String {
        UIMessage.addMsg(false, attrs, "Accès refusé", "", "Connectez-vous pour accéder à cette page")
        return "redirect:/"
    }


}