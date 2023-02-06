package edu.spring.td2.services

import edu.spring.td2.entities.Organization
import edu.spring.td2.repositories.OrganizationRepository
import edu.spring.td2.services.ui.UIForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrganizationService {

    @Autowired
    lateinit var organizationRepository: OrganizationRepository

    fun getUIForm(orga : Organization ) : UIForm.Form {
        val form = UIForm.Form("Modifier une organisation", "POST", "orgas")
        form.addField(UIForm.FormField("id", "hidden", "", orga.id.toString()))
        form.addField(UIForm.FormField("name", "text", "Nom", orga.name))
        form.addField(UIForm.FormField("domain", "text", "Domaine", orga.domain!!))
        form.addField(UIForm.FormField("aliases", "text", "Alias", orga.aliases!!))
        return form
    }

}