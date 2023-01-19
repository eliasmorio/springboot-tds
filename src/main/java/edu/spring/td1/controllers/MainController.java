package edu.spring.td1.controllers;

import edu.spring.td1.models.Category;
import edu.spring.td1.models.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"items", "categories"})
public class MainController {

    @ModelAttribute("items")
    public List<Element> getItems(){
        return new ArrayList<>();
    }

    @ModelAttribute("categories")
    public List<Category> getCategories(){
        var categories = new ArrayList<Category>();
        var category = new Category();
        category.setName("Amis");
        categories.add(category);
        category = new Category();
        category.setName("Famille");
        categories.add(category);
        category = new Category();
        category.setName("Professionnels");
        categories.add(category);
        return categories;
    }



    @GetMapping("/")
    public String index(ModelMap modelMap,
                        @ModelAttribute("categories") List<Category> categories){
        modelMap.addAttribute("categories", categories);
        return "index";
    }

    @RequestMapping(value = "/items", method = {RequestMethod.GET, RequestMethod.POST})
    public String show(ModelMap model,
                       @ModelAttribute("items") List<Element> items){
        model.addAttribute("items", items);
        return "items/show";
    }

    @GetMapping("/items/new")
    public String add(ModelMap model,
                      @ModelAttribute("categories") List<Category> categories){
        model.addAttribute("categories", categories);
        return "items/add";
    }

    @PostMapping("/items/addNew")
    public RedirectView store(@RequestParam String name,
                              @RequestParam String category,
                              @ModelAttribute("items") List<Element> items,
                              @ModelAttribute("categories") List<Category> categories){
        if (items.stream().noneMatch(el -> el.getName().equals(name))){
            Element el = new Element();
            el.setName(name);
            items.add(el);
            categories.stream().filter(cat -> cat.getName().equals(category))
                    .findFirst().get()
                    .addElement(el);
            return new RedirectView("/items");
        }
        return new RedirectView("/items/new");
    }

    @GetMapping("/items/inc/{nom}")
    public RedirectView inc(@PathVariable(value = "nom")String name,
                            @ModelAttribute("items") List<Element> items){
        for (Element el : items){
            if (el.getName().equals(name)){
                el.setEvaluation(el.getEvaluation() + 1);
                break;
            }
        }        return new RedirectView("/items");
    }

    @GetMapping("/items/dec/{nom}")
    public RedirectView dec(@PathVariable(value = "nom")String name,
                            @ModelAttribute("items") List<Element> items){
        for (Element el : items){
            if (el.getName().equals(name)){
                el.setEvaluation(el.getEvaluation() - 1);
                break;
            }
        }
        return new RedirectView("/items");
    }

    @GetMapping("/items/delete/{nom}")
    public RedirectView delete(@PathVariable(value = "nom")String name,
                               @ModelAttribute("items") List<Element> items){
        for (Element el : items){
            if (el.getName().equals(name)){
                items.remove(el);
                break;
            }
        }
        return new RedirectView("/items");
    }




}
