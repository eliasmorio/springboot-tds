package edu.spring.btp.controllers

import edu.spring.btp.entities.User
import edu.spring.btp.repositories.UserRepository
import edu.spring.btp.service.DbUserService
import edu.spring.dogs.services.ui.UIMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.HttpClientErrorException.Unauthorized
import org.springframework.web.client.HttpServerErrorException.InternalServerError
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@ControllerAdvice
@Controller
class MainController {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var userDetailsService: UserDetailsService


    @RequestMapping("/error403")
    @ExceptionHandler(Unauthorized::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun error403(attrs : RedirectAttributes): String {
        UIMessage.addMsg(false, attrs, "Accès refusé", "", "Connectez-vous pour accéder à cette page")
        return "redirect:/"
    }

    @RequestMapping("/error404")
    @ExceptionHandler(NotFound::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun error404(attrs : RedirectAttributes): String {
        UIMessage.addMsg(false, attrs, "Page introuvable", "", "La page que vous avez demandé n'existe pas")
        return "redirect:/"
    }

    @RequestMapping("/error500")
    @ExceptionHandler(InternalServerError::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun error500(attrs : RedirectAttributes): String {
        UIMessage.addMsg(false, attrs, "Erreur interne", "", "Une erreur interne est survenue")
        return "redirect:/"
    }

    @RequestMapping("/login-error")
    fun errorLogin(attrs : RedirectAttributes): String {
        UIMessage.addMsg(false, attrs, "Erreur de connexion", "", "Identifiants incorrects")
        return "redirect:/login"
    }

    @RequestMapping("/login-success")
    fun successLogin(attrs : RedirectAttributes): String {
        UIMessage.addMsg(true, attrs, "Connexion réussie", "", "Vous êtes maintenant connecté")
        return "redirect:/"
    }

    @GetMapping("/signup")
    fun signup(): String {
        return "forms/signupForm"
    }

    @PostMapping("/signup")
    fun signupPost(user: User): String {
        user.password = (userDetailsService as DbUserService).passwordEncoder.encode(user.password)
        userRepository.save(user)
        return "redirect:/login"
    }
}