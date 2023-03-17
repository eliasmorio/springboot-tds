package edu.spring.stories.controllers

import io.github.jeemv.springboot.vuejs.VueJS
import io.github.jeemv.springboot.vuejs.parts.VueMethod
import io.github.jeemv.springboot.vuejs.utilities.Http
import io.github.jeemv.springboot.vuejs.utilities.JsArray
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import java.util.*

@Controller
class SPAController {

    @Autowired
    lateinit var vue:VueJS

    @ModelAttribute("vue")
    fun vue() : VueJS = vue

    @GetMapping(path = ["/","","/index"])
    fun index(): String {
        vue.addDataRaw("developers", "[]")
        vue.addDataRaw("developer", "{}")

        vue.onMounted(
            Http.get("/developers",
                Http.responseArrayToArray("this.developers","developers"),
                "console.log('Erreur sur chargement des données!');"
            )
        )

        vue.addMethod("add",
            Http.post("/developers",
                "newDeveloper",
                        JsArray.add("this.developers","newDeveloper")+
                        "this.developer={};",
                "console.log('Erreur sur ajout de developpeur!')"
            ),
            "developer"
        )

        vue.addMethod("remove",
            Http.delete("'/developers/'+developer.id",
                JsArray.remove("this.developers","developer")+
                        JsArray.addAll("this.stories","developer.stories")
            ),
            "developer")





//        vue.addDataRaw("message","{title:'',content:''}")
//        vue.addMethod("showMessage",
//            "this.message={error: error,title: title, content: content, display: true};"+
//                    "setTimeout(function(){this.message.display=false;}.bind(this),5000);",
//            "title","content","error")
//        vue.addMethod("successMessage",
//            "this.showMessage(title,content,false);",
//            "title","content")
//        vue.addMethod("errorsMessage",
//            "this.showMessage(title,content,true);",
//            "title","content")
//        vue.addGlobalComponent("AppMessage").setProps("title","content","error","visible").setDefaultTemplateFile()
        return "/spa/index"
    }

    fun addMethodAdd(o : Any){
        val name = o.javaClass.simpleName.lowercase(Locale.getDefault())
        vue.addDataRaw("new$name","{}")
        vue.addMethod("add$name",
            Http.post("'/${name}s'",
                "new$name",
                Http.setResponseToVariable("new$name") +
                        JsArray.add("this.${name}s","new$name")+
                        "this.new$name={};",
                "console.log('Erreur sur ajout de developpeur!')"
            ),
            "new$name")
    }


}