package edu.spring.stories.controllers

import edu.spring.stories.entities.Developer
import edu.spring.stories.entities.Story
import edu.spring.stories.repositories.DeveloperRepository
import edu.spring.stories.repositories.StoryRepository
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
    lateinit var storyRepository: StoryRepository
    @Autowired
    lateinit var developerRepository: DeveloperRepository

    @GetMapping("", "/")
    fun index(model : ModelMap): String {
        val nbDeveloper = developerRepository.count()
        model["nbDevelopers"] = nbDeveloper
        if (nbDeveloper > 0){
            model["developers"] = developerRepository.findAll()
        }
        else{
            model["noDeveloper"] = true
        }
        val nbStoriesToAffect = storyRepository.findByDeveloperIsNull().size
        model["nbStoriesToAffect"] = nbStoriesToAffect
        if (nbStoriesToAffect > 0){
            model["storiesToAffect"] = storyRepository.findByDeveloperIsNull()
        }
        else{
            model["noStoryToAffect"] = true
        }

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
        @ModelAttribute("name") storyName: String,
    ) : String {
        val story = Story(storyName)
        val developer = developerRepository.findById(developerId).get()
        developer.addStory(story)
        developerRepository.save(developer)
        return "redirect:/"
    }

    @GetMapping("/story/{id}/giveup")
    fun giveUpStory(
        @PathVariable("id") storyId: Int
    ) : String {
        val story = storyRepository.findById(storyId).get()
        val dev = story.developer
        dev?.giveUpStory(story)
        storyRepository.save(story)
        return "redirect:/"
    }

    @GetMapping("/developer/{id}/delete")
    fun deleteDeveloper(@PathVariable id: Int) : String {
        developerRepository.deleteById(id)
        return "redirect:/"
    }

    @PostMapping("/story/{id}/action")
    fun storyAction(
        @ModelAttribute("story-action") action: String,
        @ModelAttribute("developer-id") developerId: String,
        @PathVariable("id") storyId: Int
    ) : String
    {
        val story = storyRepository.findById(storyId).get()
        when (action) {
            "affect" -> {
                val developer = developerRepository.findById(developerId.toInt()).get()
                developer.addStory(story)
                developerRepository.save(developer)
            }
            "remove" -> {
                storyRepository.delete(story)
            }
            else -> {
                throw Exception("Action Inconnue : $action")
            }
        }
        return "redirect:/"
    }

}