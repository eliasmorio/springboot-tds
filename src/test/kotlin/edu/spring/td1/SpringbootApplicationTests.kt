package edu.spring.td1

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class SpringbootApplicationTests {

	@Autowired
	var mvc: MockMvc? = null

	@Test
	fun contextLoads() {
	}

	@Test
	fun testIndex() {
		mvc!!.perform(get("/"))
				.andExpect(status().isOk)
				.andExpect(view().name("items/show"))
				.andExpect(model().attributeExists("categories"))
	}

}
