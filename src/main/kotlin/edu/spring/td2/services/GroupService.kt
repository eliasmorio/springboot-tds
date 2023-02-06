package edu.spring.td2.services

import edu.spring.td2.entities.Group
import edu.spring.td2.repositories.GroupRepository
import edu.spring.td2.services.ui.UIDisplay
import edu.spring.td2.services.ui.UIForm
import edu.spring.td2.services.ui.UITable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.management.Query

@Service
class GroupService {

    @Autowired
    lateinit var groupRepository: GroupRepository

    fun getUIForm(group : Group ) : UIForm.Form {
        val form = UIForm.Form("Modifier un groupe", "POST")
        if (group.id != null) {
            form.addField(UIForm.inputField("id", "hidden", "", group.id.toString()))
        }
        form.addField(UIForm.inputField("name", "text", "Nom", group.name?:""))
        form.addField(UIForm.inputField("email", "text", "Email", group.email?:""))
        return form
    }

    fun getUITable(groups:List<Group>? = null) : UITable.Table {
        val headers = arrayListOf("Id", "Nom", "Email")
        val rows = arrayListOf<UITable.Row>()
        if (groups == null){
            groupRepository.findAll().forEach { group ->
                rows.add(UITable.Row(arrayListOf(group.id.toString(), group.name?:"", group.email?:""), group.id.toString()))
            }
        } else {
            groups.forEach { group ->
                rows.add(UITable.Row(arrayListOf(group.id.toString(), group.name?:"", group.email?:""), group.id.toString()))
            }
        }
        return UITable.table("Liste des groupes",  headers, rows)
    }

    fun getUIDisplay(group : Group) : UIDisplay.Table {
        val fields = arrayListOf<UIDisplay.Field>()
        fields.add(UIDisplay.field("Id", group.id.toString()))
        fields.add(UIDisplay.field("Nom", group.name?:""))
        fields.add(UIDisplay.field("Email", group.email?:""))
        fields.add(UIDisplay.field("Organisation", group.organization?.name?:""))
        fields.add(UIDisplay.field("Utilisateurs", group.users.joinToString("<br>") { user -> "${user.firstname} ${user.lastname}" } ?:""))
        return UIDisplay.table(fields)
    }

    fun addDefaults(model: MutableMap<String, Any>) {
        model["object"] = "Groupe"
        model["url"] = "groups"
    }

    fun getUITableSearch(query: String):UITable.Table {
        val groups = groupRepository.findByNameContainingOrEmailContainingOrAliasesContaining(query, query, query)
        return getUITable(groups)
    }

}