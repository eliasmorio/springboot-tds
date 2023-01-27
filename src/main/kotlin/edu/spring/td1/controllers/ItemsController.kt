package edu.spring.td1.controllers

import edu.spring.td1.models.Item
import edu.spring.td1.services.UIMessage
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

@Controller
@SessionAttributes("items")
class ItemsController {

    @get:ModelAttribute("items")
    val items: Set<Item>
        get() = HashSet()



    @RequestMapping(value = ["/"], method = [RequestMethod.GET, RequestMethod.POST])
    fun show(model: ModelMap,
             @RequestAttribute msg:UIMessage.Message?,
             @ModelAttribute("items") items : HashSet<Item>): String {
        model.addAttribute("items", items)
        return "items/show"
    }

    @GetMapping("/new")
    fun add(model: ModelMap): String? {
        return "items/add"
    }

    @PostMapping("/addNew")
    fun store(@ModelAttribute item:Item,
              @SessionAttribute("items") items : HashSet<Item>,
              attrs:RedirectAttributes): RedirectView {
        if (items.add(item)){
            attrs.addFlashAttribute("msg", UIMessage.message("Ajout", "${item.name} a été ajouté avec succès"))
        } else {
            attrs.addFlashAttribute("msg", UIMessage.message("Ajout", "${item.name} est déja dans la liste des items", "error", "warning circle"))
        }

        return RedirectView("/")
    }

    @GetMapping("/inc/{nom}")
    fun inc(@PathVariable(value = "nom") name: String,
            @SessionAttribute("items") items: HashSet<Item>): RedirectView {
        items.stream()
                .filter { el: Item -> el.name == name }
                .forEach { el: Item -> el.evaluation += 1 }
        return RedirectView("/")
    }

    @GetMapping("/dec/{nom}")
    fun dec(@PathVariable(value = "nom") name: String,
            @SessionAttribute("items") items: HashSet<Item>): RedirectView {
        items.stream()
                .filter { el: Item -> el.name == name }
                .forEach { el: Item -> el.evaluation -= 1 }
        return RedirectView("/")
    }

    @GetMapping("/delete/{nom}")
    fun delete(@PathVariable(value = "nom") name:String,
               @SessionAttribute("items") items: HashSet<Item>): RedirectView {
        items.removeIf{el: Item -> el.name == name }
        return RedirectView("/")
    }
}