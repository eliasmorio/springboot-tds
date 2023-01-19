package edu.spring.td1.controllers;

import edu.spring.td1.models.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SessionAttributes("items")
@Controller
public class MainController {

    @ModelAttribute("items")
    public List<Element> getItems(){
        return new ArrayList<>();
    }

    @RequestMapping(value = "/items", method = {RequestMethod.GET, RequestMethod.POST})
    public String show(ModelMap model){
        model.addAttribute("items", getItems());
        return "items/show";
    }






}
