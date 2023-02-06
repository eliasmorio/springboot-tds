package edu.spring.td2.services

import edu.spring.td2.entities.User
import edu.spring.td2.repositories.OrganizationRepository
import edu.spring.td2.repositories.UserRepository
import edu.spring.td2.services.ui.UIDisplay
import edu.spring.td2.services.ui.UIForm
import edu.spring.td2.services.ui.UITable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var organizationRepository: OrganizationRepository


    fun getUIForm(user: User) : UIForm.Form {
        val form = UIForm.Form("Ajouter un utilisateur", "POST")
        if (user.id != null) {
            form.addField(UIForm.inputField("id", "hidden", "", user.id.toString()))
        }
        form.addField(UIForm.inputField("firstname", "text", "Prénom", user.firstname?:""))
        form.addField(UIForm.inputField("lastname", "text", "Nom", user.lastname?:""))
        form.addField(UIForm.inputField("email", "email", "Email", user.email?:""))
        val organizations = arrayListOf<UIForm.FormOption>()

        organizations.add(UIForm.selectOption("-1", "Sélectionnez une organisation", true))

        organizationRepository.findAll().forEach {
            organizations.add(UIForm.selectOption(it.id.toString(), it.name?:"", user.organization?.id == it.id))
        }
        form.addField(UIForm.selectField("organization", "select", "Organisation", organizations))
        return form
    }

    fun getUITable(users : List<User>? = null) : UITable.Table {
        val headers = arrayListOf("Id", "Prénom", "Nom", "Email")
        val rows = arrayListOf<UITable.Row>()
        if (users == null) {
            userRepository.findAll().forEach {
                rows.add(UITable.Row(arrayListOf(it.id.toString(), it.firstname?:"", it.lastname?:"", it.email?:""), it.id.toString()))
            }
        } else{
            users.forEach {
                rows.add(UITable.Row(arrayListOf(it.id.toString(), it.firstname?:"", it.lastname?:"", it.email?:""), it.id.toString()))
            }
        }
        return UITable.table("Liste des utilisateurs", headers, rows)
    }

    fun getUIDetailTable(user: User) : UIDisplay.Table {
        val fields = arrayListOf<UIDisplay.Field>()
        fields.add(UIDisplay.field("Id", user.id.toString()))
        fields.add(UIDisplay.field("Prénom", user.firstname?:""))
        fields.add(UIDisplay.field("Nom", user.lastname?:""))
        fields.add(UIDisplay.field("Email", user.email?:""))
        fields.add(UIDisplay.field("Groupes", user.groups.joinToString("<br>") { group -> group.name?:"" } ?:""))
        fields.add(UIDisplay.field("Organisation", user.organization?.name?:""))
        return UIDisplay.table(fields)
    }

    fun addDefaults(model: MutableMap<String, Any>) {
        model["object"] = "Utilisateur"
        model["url"] = "users"
    }

    fun getUITableSearch(query: String): Any? {
        val users = userRepository.findByFirstnameContainingOrLastnameContainingOrEmailContaining(query, query, query)
        return getUITable(users)

    }

}