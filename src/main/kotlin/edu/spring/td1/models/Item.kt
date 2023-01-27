package edu.spring.td1.models

class Item(var name:String) {
    var evaluation = 0

    override fun equals(obj: Any?): Boolean {
        return if (obj is Item) {
            name == obj.name
        } else false
    }

    override fun hashCode(): Int {
        return name.hashCode() ?: 0
    }
}