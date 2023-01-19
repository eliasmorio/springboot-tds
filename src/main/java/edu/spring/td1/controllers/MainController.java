package edu.spring.td1.controllers;

import edu.spring.td1.models.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("items")
public class MainController {

    @ModelAttribute("items")
    public List<Element> getItems(){
        return new ArrayList<>();
    }

    @RequestMapping(value = "/items", method = {RequestMethod.GET, RequestMethod.POST})
    public String show(ModelMap model,
                       @ModelAttribute("items") List<Element> items){
        model.addAttribute("items", items);
        return "items/show";
    }

    @GetMapping("/items/new")
    public String add(){
        return "items/add";
    }

    @PostMapping("/items/addNew")
    public RedirectView store(@RequestParam String name,
                              @ModelAttribute("items") List<Element> items){
        Element el = new Element();
        el.setName(name);
        items.add(el);
        return new RedirectView("/items");
    }




}
