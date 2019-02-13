package uz.zokirbekov.todo.util

interface ItemClickListener<T>
{
    fun OnItemClick(obj:T,position: Int)
    fun OnDeleteClick(obj:T)
}