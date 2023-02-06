package edu.spring.td2.services

import edu.spring.td2.entities.Organization
import edu.spring.td2.repositories.OrganizationRepository
import edu.spring.td2.services.ui.UIDisplay
import edu.spring.td2.services.ui.UIForm
import edu.spring.td2.services.ui.UITable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrganizationService {

    @Autowired
    lateinit var organizationRepository: OrganizationRepository

    fun getUIForm(orga : Organization ) : UIForm.Form {
        val form = UIForm.Form("Modifier une organisation", "POST", "orgas")
        if (orga.id != null) {
            form.addField(UIForm.inputField("id", "hidden", "", orga.id.toString()))
        }
        form.addField(UIForm.inputField("name", "text", "Nom", orga.name?:""))
        form.addField(UIForm.inputField("domain", "text", "Domaine", orga.domain?:""))
        form.addField(UIForm.inputField("aliases", "text", "Alias", orga.aliases?:""))
        return form
    }

    fun getUITable() : UITable.Table {
        val headers = arrayListOf("Id", "Nom", "Domaine", "Alias")
        val rows = arrayListOf<UITable.Row>()
        organizationRepository.findAll().forEach {
            rows.add(UITable.Row(arrayListOf(it.id.toString(), it.name?:"", it.domain?:"", it.aliases?:""), it.id.toString()))
        }
        return UITable.table("Liste des organisations", "orgas",  headers, rows)
    }

    fun getUIDisplay(orga : Organization) : UIDisplay.Table {
        val fields = arrayListOf<UIDisplay.Field>()
        fields.add(UIDisplay.field("Id", orga.id.toString()))
        fields.add(UIDisplay.field("Nom", orga.name?:""))
        fields.add(UIDisplay.field("Domaine", orga.domain?:""))
        fields.add(UIDisplay.field("Alias", orga.aliases?:""))
        fields.add(UIDisplay.field("Groupes", orga.groups.joinToString("<br>") { it.name?:""} ?:""))
        fields.add(UIDisplay.field("Utilisateurs", orga.users.joinToString("<br>") { user -> "${user.firstname} ${user.lastname}" } ?:""))
        return UIDisplay.table(fields)
    }

}