package edu.spring.td2.services.ui

class UIDisplay {

    class Table(var fields : List<Field>)

    class Field(var label : String,var value : String)

    companion object {
        fun table(fields : List<Field>) : Table {
            return Table(fields)
        }
        fun field(label : String, value : String) : Field {
            return Field(label, value)
        }
    }
}