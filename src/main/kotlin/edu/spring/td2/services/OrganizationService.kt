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
        val form = UIForm.Form("Modifier une organisation", "POST")
        if (orga.id != null) {
            form.addField(UIForm.inputField("id", "hidden", "", orga.id.toString()))
        }
        form.addField(UIForm.inputField("name", "text", "Nom", orga.name?:""))
        form.addField(UIForm.inputField("domain", "text", "Domaine", orga.domain?:""))
        form.addField(UIForm.inputField("aliases", "text", "Alias", orga.aliases?:""))
        return form
    }

    fun getUITable(orgas : List<Organization>? = null) : UITable.Table {
        val headers = arrayListOf("Id", "Nom", "Domaine", "Alias")
        val rows = arrayListOf<UITable.Row>()
        if (orgas == null) {
            organizationRepository.findAll().forEach { orga ->
                rows.add(UITable.Row(arrayListOf(orga.id.toString(), orga.name?:"", orga.domain?:"", orga.aliases?:""), orga.id.toString()))
            }
        } else {
            orgas.forEach { orga ->
                rows.add(UITable.Row(arrayListOf(orga.id.toString(), orga.name?:"", orga.domain?:"", orga.aliases?:""), orga.id.toString()))
            }
        }
        return UITable.table("Liste des organisations",  headers, rows)
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

    fun addDefaults(model: MutableMap<String, Any>) {
        model["object"] = "Organisation"
        model["url"] = "orgas"
    }

    fun getUITableSearch(query: String): UITable.Table {
        val orgas = organizationRepository.findByNameContainingOrDomainContainingOrAliasesContaining(query, query, query)
        return getUITable(orgas)
    }

}