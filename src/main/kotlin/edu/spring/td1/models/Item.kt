package edu.spring.td1.models

data class Item(var name:String?) {
    var evaluation:Int = 0

//    override fun equals(obj: Any?): Boolean {
//        return if (obj is Item) {
//            name == obj.name
//        } else false
//    }
//
//    override fun hashCode(): Int {
//        return name.hashCode() ?: 0
//    }
}