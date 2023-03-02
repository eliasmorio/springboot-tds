package edu.spring.stories

import edu.spring.stories.entities.Developer
import edu.spring.stories.repositories.DeveloperRepository
import edu.spring.stories.repositories.StoryRepository
import edu.spring.stories.repositories.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController  {
    @Autowired
    lateinit var tagRepository: TagRepository
    @Autowired
    lateinit var storyRepository: StoryRepository
    @Autowired
    lateinit var developerRepository: DeveloperRepository

    @GetMapping("", "/")
    fun index(model : ModelMap): String {
        val developer = Developer("John", "Doe")
        developerRepository.save(developer)
        model["developers"] = developerRepository.findAll()
        model["storiesToAffect"] = storyRepository.findStoriesToAssign()
        model["nbDevelopers"] = developerRepository.count()
        model["nbStoriesToAffect"] = storyRepository.findStoriesToAssign()?.size ?: 0
        return "index"
    }
}