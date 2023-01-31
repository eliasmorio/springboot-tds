package edu.spring.td2

import edu.spring.td2.repositories.OrganizationRepository
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class Td2ApplicationTests {

    @Autowired
    var mvc: MockMvc? = null
    @Autowired
    var organizationRepository: OrganizationRepository? = null

    @Test
    fun contextLoads() {
    }

    @Test
    fun testOrgasIndex() {
        //verify that the Organization index page display the name of all organizations
        val orgas = organizationRepository!!.findAll()
        for (orga in orgas) {
            mvc!!.perform(get("/orgas"))
                    .andExpect(status().isOk)
                    .andExpect(content().string(containsString(orga.name)))
        }
    }





}
