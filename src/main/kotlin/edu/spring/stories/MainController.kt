package edu.spring.stories

import edu.spring.stories.entities.Developer
import edu.spring.stories.entities.Story
import edu.spring.stories.repositories.DeveloperRepository
import edu.spring.stories.repositories.StoryRepository
import edu.spring.stories.repositories.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

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
        model["developers"] = developerRepository.findAll()
        model["storiesToAffect"] = storyRepository.findStoriesToAssign()
        model["nbDevelopers"] = developerRepository.count()
        model["nbStoriesToAffect"] = storyRepository.findStoriesToAssign()?.size ?: 0
        return "index"
    }

    @PostMapping("/developer/add")
    fun addDeveloper(
        @ModelAttribute("firstname") firstname: String,
        @ModelAttribute("lastname") lastname: String
    ) : String {
        val developer = Developer(firstname, lastname)
        developerRepository.save(developer)
        return "redirect:/"
    }

    @PostMapping("/developer/{id}/story")
    fun addStoryToDeveloper(
        @PathVariable("id") developerId: Int,
        @ModelAttribute("storyName") storyName: String,
    ) : String {
        val story = Story(storyName)
        val developer = developerRepository.findById(developerId).get()
        developer.addStory(story)
        developerRepository.save(developer)
        return "redirect:/"
    }
}