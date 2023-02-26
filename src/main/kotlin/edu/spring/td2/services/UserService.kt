package edu.spring.td2.services

import edu.spring.td2.entities.User
import edu.spring.td2.repositories.GroupRepository
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
    @Autowired
    lateinit var organizationService: OrganizationService
    @Autowired
    lateinit var groupRepository: GroupRepository


    fun getUIForm(user: User) : UIForm.Form {
        val form = UIForm.Form("Ajouter un utilisateur", "POST")
        if (user.id != null) {
            form.addField(UIForm.inputField("id", "hidden", "", user.id.toString()))
        }
        form.addField(UIForm.inputField("firstname", "text", "Prénom", user.firstname?:""))
        form.addField(UIForm.inputField("lastname", "text", "Nom", user.lastname?:""))
        form.addField(UIForm.inputField("email", "email", "Email", user.email?:""))
        form.addField(organizationService.getUISelect(user.organization?.id))
        val groups = hashMapOf<String, String>()
        groupRepository.findAll().forEach {
            groups[it.id.toString()] = it.name?:""
        }
        form.addField(UIForm.multiSelectField("groups", "Groupes", groups, user.groups.map { it.id.toString() }))
        return form
    }

    fun getUITable(users : List<User>? = null, user: User? = null) : UITable.Table {
        val headers = arrayListOf("Id", "Prénom", "Nom", "Email")
        val rows = arrayListOf<UITable.Row>()
        if (users == null) {
            userRepository.findAll().forEach {
                rows.add(UITable.Row(arrayListOf(it.id.toString(), it.firstname?:"", it.lastname?:"", it.email?:""), it.id.toString(), it.id == user?.id))
            }
        } else{
            users.forEach {
                rows.add(UITable.Row(arrayListOf(it.id.toString(), it.firstname?:"", it.lastname?:"", it.email?:""), it.id.toString(), it.id == user?.id))
            }
        }
        return UITable.table("Liste des utilisateurs", headers, rows)
    }

    fun getDetails(user:User) : UIDisplay.Table {
        val fields = arrayListOf<UIDisplay.Field>()
        fields.add(UIDisplay.field("Groupes", user.groups.joinToString("<br>") { group -> group.name?:"" } ?:""))
        fields.add(UIDisplay.field("Organisation", user.organization?.name?:""))
        return UIDisplay.table(fields)
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