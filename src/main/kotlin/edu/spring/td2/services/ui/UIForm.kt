package edu.spring.td2.services.ui

import edu.spring.td2.repositories.GroupRepository
import edu.spring.td2.repositories.OrganizationRepository
import edu.spring.td2.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

class UIForm() {


    class Form(var label : String, var method : String) {
        val fields: MutableList<FormField> = mutableListOf()

        fun addField(field: FormField) {
            fields.add(field)
        }

    }

    class FormField(var name : String, var type : String,
                    var label : String, var value : String,
                    var options : List<FormOption>? = null,
                    var input : Boolean = true, var select : Boolean = false,
                    var multiSelect : Boolean = false, var textArea : Boolean = false,
                    var selectedOptions : String = "") {
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

        fun multiSelectField(name : String, label : String, value : String, options : List<FormOption>) : FormField {
            var selectedOptions = ""
            for (option in options)
                if (option.selected)
                    selectedOptions += option.value + ","
            if (selectedOptions.isNotEmpty())
                selectedOptions = selectedOptions.substring(0, selectedOptions.length - 1)
            return FormField(name, "select", label, value, options, input = false, multiSelect=true, selectedOptions=selectedOptions)
        }

        fun textAreaField(name : String, label : String, value : String) : FormField {
            return FormField(name, "textarea", label, value, input = false  , textArea=true)
        }

        fun inputField(name : String, type : String, label : String, value : String) : FormField {
            return FormField(name, type, label, value, arrayListOf())
        }

        fun multiSelectField(name : String, label: String, options : HashMap<String, String>, selectedOptions: List<String>) : FormField {
            val optionsList = arrayListOf<FormOption>()
            options.forEach { (key, value) ->
                optionsList.add(selectOption(key, value, selectedOptions.contains(key)))
            }
            return multiSelectField(name, label, "", optionsList)
        }


    }


}