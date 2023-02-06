package edu.spring.td2.controllers

import edu.spring.td2.repositories.GroupRepository
import edu.spring.td2.repositories.OrganizationRepository
import edu.spring.td2.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {

    @Autowired
    lateinit var groupRepository: GroupRepository
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var organizationRepository: OrganizationRepository

    @GetMapping("/", "")
    fun index(model: ModelMap) : String {
        model["nGroups"] = groupRepository.count()
        model["nUsers"] = userRepository.count()
        model["nOrgas"] = organizationRepository.count()
        return "index"
    }
}