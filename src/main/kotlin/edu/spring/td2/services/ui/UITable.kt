package edu.spring.td2.services.ui

class UITable {

    class Table(val label : String, val columns : List<String>, val rows : List<Row>) {
    }

    class Row(val columns : List<String>, val id : String? = null, val selected : Boolean = false) {
    }



    companion object {
        fun table(label : String, columns : List<String>, rows : List<Row>) : Table {
            return Table(label, columns, rows)
        }

    }

}