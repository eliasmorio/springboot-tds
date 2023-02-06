package edu.spring.td2.services

import edu.spring.td2.entities.Group
import edu.spring.td2.repositories.GroupRepository
import edu.spring.td2.services.ui.UIForm
import edu.spring.td2.services.ui.UITable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GroupService {

    @Autowired
    lateinit var groupRepository: GroupRepository

    fun getUIForm(group : Group ) : UIForm.Form {
        val form = UIForm.Form("Modifier un groupe", "POST", "groups")
        if (group.id != null) {
            form.addField(UIForm.inputField("id", "hidden", "", group.id.toString()))
        }
        form.addField(UIForm.inputField("name", "text", "Nom", group.name?:""))
        form.addField(UIForm.inputField("email", "text", "Email", group.email?:""))
        return form
    }

    fun getUITable() : UITable.Table {
        val headers = arrayListOf("Id", "Nom", "Email")
        val rows = arrayListOf<UITable.Row>()
        groupRepository.findAll().forEach {
            rows.add(UITable.Row(arrayListOf(it.id.toString(), it.name?:"", it.email?:""), it.id.toString()))
        }
        return UITable.table("Liste des groupes", "groups",  headers, rows)
    }
}