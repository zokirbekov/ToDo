package uz.zokirbekov.todo.util

interface ItemClickListener
{
    fun <T>OnItemClick(obj:T,position: Int)
    fun OnDeleteClick(id:Int)
}