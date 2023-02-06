package edu.spring.td2.services

import edu.spring.td2.entities.User
import edu.spring.td2.repositories.UserRepository
import edu.spring.td2.services.ui.UIForm
import edu.spring.td2.services.ui.UITable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.reflect.Array

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository


    fun getUIForm(user: User) : UIForm.Form {
        val form = UIForm.Form("Ajouter un utilisateur", "POST", "users")
        if (user.id != null) {
            form.addField(UIForm.inputField("id", "hidden", "", user.id.toString()))
        }
        form.addField(UIForm.inputField("firstname", "text", "Prénom", user.firstname?:""))
        form.addField(UIForm.inputField("lastname", "text", "Nom", user.lastname?:""))
        form.addField(UIForm.inputField("email", "email", "Email", user.email?:""))
        val organizations = arrayListOf<UIForm.FormOption>()
        organizations.add(UIForm.selectOption("-1", "Sélectionnez une organisation", true))
        userRepository.findAll().forEach {
            organizations.add(UIForm.selectOption(it.id.toString(), "${it.firstname} ${it.lastname}", user.organization?.id == it.id))
        }
        form.addField(UIForm.selectField("organization", "select", "Organisation", organizations))
        return form
    }

    fun getUITable() : UITable.Table {
        val headers = arrayListOf("Id", "Prénom", "Nom", "Email")
        val rows = arrayListOf<UITable.Row>()
        userRepository.findAll().forEach {
            rows.add(UITable.Row(arrayListOf(it.id.toString(), it.firstname?:"", it.lastname?:"", it.email?:""), it.id.toString()))
        }
        return UITable.table("Liste des utilisateurs", "users",  headers, rows)
    }

}