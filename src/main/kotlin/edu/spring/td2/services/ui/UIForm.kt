package edu.spring.td2.services.ui

class UIForm() {
    class Form(var label : String, var method : String) {
        val fields: MutableList<FormField> = mutableListOf()

        fun addField(field: FormField) {
            fields.add(field)
        }

    }

    class FormField(var name : String, var type : String, var label : String, var value : String, var options : List<FormOption>? = null, var input : Boolean = true, var select : Boolean = false, var textArea : Boolean = false) {

    }

    class FormOption(var value : String, var label : String, var selected : Boolean) {
    }

    companion object {
        fun selectField(name : String, label : String, value : String, options : List<FormOption>) : FormField {
            return FormField(name, "select", label, value, options, input = false, select=true)
        }

        fun selectOption(value : String, label : String, selected : Boolean = false) : FormOption {
            return FormOption(value, label, selected)
        }

        fun textAreaField(name : String, label : String, value : String) : FormField {
            return FormField(name, "textarea", label, value, input = false  , textArea=true)
        }

        fun inputField(name : String, type : String, label : String, value : String) : FormField {
            return FormField(name, type, label, value, arrayListOf())
        }






    }


}