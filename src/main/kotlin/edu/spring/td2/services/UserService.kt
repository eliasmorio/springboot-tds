package edu.spring.td2.services

import edu.spring.td2.entities.User
import edu.spring.td2.repositories.UserRepository
import edu.spring.td2.services.ui.UIForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.reflect.Array

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository


    fun getUIForm(user: User) : UIForm.Form {
        val form = UIForm.Form("Ajouter un utilisateur", "POST", "users")
        form.addField(UIForm.inputField("firstname", "text", "Prénom", ""))
        form.addField(UIForm.inputField("lastname", "text", "Nom", ""))
        form.addField(UIForm.inputField("email", "email", "Email", ""))
        val organizations = arrayListOf<UIForm.FormOption>()
        organizations.add(UIForm.FormOption("-1", "Sélectionnez une organisation", true))
        userRepository.findAll().forEach {
            organizations.add(UIForm.FormOption(it.id.toString(), "${it.firstname} ${it.lastname}", user.organization?.id == it.id))
        }
        form.addField(UIForm.selectField("organization", "select", "Organisation", organizations))
        return form
    }

}