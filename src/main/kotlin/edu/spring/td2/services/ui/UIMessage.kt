package edu.spring.td2.services.ui

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


    }
}