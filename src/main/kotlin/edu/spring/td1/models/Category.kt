package edu.spring.td1.models

import java.util.HashSet

class Category(var name:String) {
    var items:HashSet<Item> = HashSet()

    fun addItem(item:Item) : Boolean {
        return items.add(item)
    }

    fun removeItem(item:Item) : Boolean {
        return items.remove(item)
    }


}