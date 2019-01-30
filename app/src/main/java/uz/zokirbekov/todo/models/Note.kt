package uz.zokirbekov.todo.models

import java.util.*

public class Note {
    var id:Int = 0
        get() = field
        set(value) {
            field = value
        }
    var note:String = ""
        get() = field
        set(value) {
            field = value
        }
    var title:String = ""
        get() = field
        set(value) {
            field = value
        }
    var create_date:Date = Date()
        get() = field
        set(value) {
            field = value
        }
    var update_date:Date = Date()
        get() = field
        set(value) {
            field = value
        }
}