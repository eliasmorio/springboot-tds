package edu.spring.td1.controllers

import edu.spring.td1.models.Category
import edu.spring.td1.models.Item
import edu.spring.td1.services.UIMessage
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

@Controller
@SessionAttributes("items", "categories")
class ItemsController {

    @get:ModelAttribute("items")
    val items: Set<Item>
        get() = HashSet()

    @get:ModelAttribute("categories")
    val categories: Set<Category>
        get() {
            val categories = HashSet<Category>()
            categories.add(Category("Amis"))
            categories.add(Category("Famille"))
            categories.add(Category("Professionnels"))
            return categories
        }



    @RequestMapping(value = ["/"], method = [RequestMethod.GET, RequestMethod.POST])
    fun show(model: ModelMap,
             @RequestAttribute msg:UIMessage.Message?,
             @ModelAttribute("items") items : HashSet<Item>,
             @ModelAttribute("categories") categories : HashSet<Item>): String {
        print(categories)
        model.addAttribute("items", items)
        model.addAttribute("categories", categories)
        return "items/show"
    }

    @GetMapping("/new")
    fun add(model: ModelMap): String? {
        return "items/add"
    }

    @PostMapping("/addNew")
    fun store(@RequestParam("name") item: Item,
              @RequestParam("category") category: Category,
              @SessionAttribute("categories") categories: HashSet<Category>,
              @SessionAttribute("items") items: HashSet<Item>,
              attrs:RedirectAttributes): RedirectView {


        val cat = categories.find { el: Category -> el.name == category.name }
        if (cat == null){
            attrs.addFlashAttribute("msg", UIMessage.message("Ajout", "La catégorie ${category.name} n'existe pas", "error", "warning circle"))
            return RedirectView("/")
        }
        if (item.name == null || item.name == ""){
            attrs.addFlashAttribute("msg", UIMessage.message("Ajout", "Le nom de l'item ne peut pas être vide", "error", "warning circle"))
            return RedirectView("/")
        }
        if (items.add(item)){
            cat.addItem(item)
            attrs.addFlashAttribute("msg", UIMessage.message("Ajout", "${item.name} a été ajouté avec succès"))
        } else {
            attrs.addFlashAttribute("msg", UIMessage.message("Ajout", "${item.name} est déja dans la liste des items", "error", "warning circle"))
        }
        categories.stream().forEach { println(it.name) }

        return RedirectView("/")
    }

    @GetMapping("/inc/{nom}")
    fun inc(@PathVariable(value = "nom") name: String,
            attrs:RedirectAttributes,
            @SessionAttribute("items") items: HashSet<Item>): RedirectView {
        items.stream()
                .filter { el: Item -> el.name == name }
                .forEach { el: Item -> el.evaluation += 1 }
        attrs.addFlashAttribute("msg", UIMessage.message("Modification", "$name à été incrémenté"))
        return RedirectView("/")
    }

    @GetMapping("/dec/{nom}")
    fun dec(@PathVariable(value = "nom") name: String,
            attrs:RedirectAttributes,
            @SessionAttribute("items") items: HashSet<Item>): RedirectView {
        items.stream()
                .filter { el: Item -> el.name == name }
                .forEach { el: Item -> el.evaluation -= 1 }
        attrs.addFlashAttribute("msg", UIMessage.message("Modification", "$name à été décrémenté"))
        return RedirectView("/")
    }

    @GetMapping("/delete/{nom}")
    fun delete(@PathVariable(value = "nom") name:String,
               attrs:RedirectAttributes,
               @SessionAttribute("items") items: HashSet<Item>,
               @SessionAttribute("categories") categories: HashSet<Category>): RedirectView {
        if (items.removeIf{el: Item -> el.name == name }){
            categories.find { el: Category -> el.items.removeIf { it.name == name} }
            attrs.addFlashAttribute("msg", UIMessage.message("Suppression", "$name à été supprimé"))
        }
        else{
            attrs.addFlashAttribute("msg", UIMessage.message("Suppression", "$name n'existe pas", "error", "warning circle"))
        }
        return RedirectView("/")
    }
}