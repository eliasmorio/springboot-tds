package edu.spring.dogs.services.ui

import org.springframework.web.servlet.mvc.support.RedirectAttributes

class UIMessage() {

    class Message(var title:String, var message: String, var type:String, var icon:String, var redirect:String? = null, var defaultRedirect:String = "/")

    companion object {
        fun message(title: String, message: String, type: String, icon: String) =
            Message(title, message, type, icon)

        fun message(title: String, message: String) =
            Message(title, message, "success", "info circle")

        fun message(title: String, message: String, type: String, icon: String, redirect: String?, defaultRedirect: String) =
            Message(title, message, type, icon, redirect, defaultRedirect)

        fun deleteMessage(objName : String, objType : String, id : Int) =
            Message("Confirmation de suppression",
                "Confirmez-vous la suppression de '$objName' ?",
                "red",
                "question circle",
                "$objType/delete/$id",
                "/$objType")

        fun addMsg(resp:Boolean, attrs: RedirectAttributes, title:String, success:String, error:String){
            if(resp) {
                attrs.addFlashAttribute("message",
                    UIMessage.message(title, success))
            } else {
                attrs.addFlashAttribute("message",
                    UIMessage.message(title, error,"error","warning circle"))
            }
        }
    }

}