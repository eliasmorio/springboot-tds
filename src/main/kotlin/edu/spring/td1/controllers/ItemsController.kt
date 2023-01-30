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

//    @get:ModelAttribute("items")
//    val items: Set<Item>
//        get() = HashSet()

    @get:ModelAttribute("categories")
    val categories: Set<Category>
        get() {
            val categories = HashSet<Category>()
            categories.add(Category("Amis"))
            categories.add(Category("Famille"))
            categories.add(Category("Professionnels"))
            return categories
        }

    private fun getItemByName(name:String,items:HashSet<Item>):Item?=items.find { name==it.name }

    private fun addMsg(resp:Boolean,attrs: RedirectAttributes,title:String,success:String,error:String){
        if(resp) {
            attrs.addFlashAttribute("msg",
                UIMessage.message(title, success))
        } else {
            attrs.addFlashAttribute("msg",
                UIMessage.message(title, error,"error","warning circle"))

        }
    }


    @RequestMapping(value = ["/"], method = [RequestMethod.GET, RequestMethod.POST])
    fun show(model: ModelMap,
             @RequestAttribute msg:UIMessage.Message?,
             @ModelAttribute("categories") categories : HashSet<Category>): String {
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
              attrs:RedirectAttributes): RedirectView {
        val cat = categories.find { it == category }
        if (cat == null)
            attrs.addFlashAttribute("msg", UIMessage.message("Ajout", "$category n'existe pas", "error", "warning circle"))
        else
            addMsg(cat.addItem(item),attrs,"Ajout","$item à été ajouté","$item existe déjà")
        return RedirectView("/")
    }

    @GetMapping("/inc/{categ}/{nom}")
    fun inc(@PathVariable(value = "nom") name: String,
            @PathVariable(value = "categ") categ: Category,
            attrs:RedirectAttributes,
            @SessionAttribute("categories") categories: HashSet<Category>): RedirectView {
        val cat = categories.find { it == categ }
        if (cat ==null)
            addMsg(false, attrs, "Modification", "$categ n'existe pas", "$categ n'existe pas")
        else {
            val item = getItemByName(name, cat.items)
            if (item != null)
                item.evaluation += 1
            addMsg(item != null, attrs, "Modification", "$name à été incrémenté", "$name n'existe pas")
        }
        return RedirectView("/")
    }

    @GetMapping("/dec/{categ}/{nom}")
    fun dec(@PathVariable(value = "nom") name: String,
            @PathVariable(value = "categ") categ: Category,
            attrs:RedirectAttributes,
            @SessionAttribute("categories") items: HashSet<Category>): RedirectView {
        val cat = items.find { it == categ }
        if (cat ==null)
            addMsg(false, attrs, "Modification", "$categ n'existe pas", "$categ n'existe pas")
        else {
            val item = getItemByName(name, cat.items)
            if (item != null)
                item.evaluation -= 1
            addMsg(item != null, attrs, "Modification", "$name à été décrémenté", "$name n'existe pas")
        }
        return RedirectView("/")
    }

    @GetMapping("/delete/{categ}/{nom}")
    fun delete(@PathVariable(value = "nom") name:String,
               @PathVariable(value = "categ") categ: Category,
               attrs:RedirectAttributes,
               @SessionAttribute("categories") categories: HashSet<Category>): RedirectView {
        val cat = categories.find { it == categ }
        if (cat ==null)
            addMsg(false, attrs, "Suppression", "$categ n'existe pas", "$categ n'existe pas")
        else {
            val item = getItemByName(name, cat.items)
            addMsg(cat.items.remove(item), attrs, "Suppression", "$name à été supprimé", "$name n'existe pas")
        }
        return RedirectView("/")
    }
}