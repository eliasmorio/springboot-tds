package edu.spring.td1.models

import java.util.HashSet

class Category(var name:String) {
    var items:HashSet<Item> = HashSet()

    fun addItem(item:Item) {
        items.add(item)
    }

    fun removeItem(item:Item) {
        items.remove(item)
    }


}