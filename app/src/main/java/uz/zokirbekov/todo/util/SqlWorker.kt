package uz.zokirbekov.todo.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.Constants
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

public class SqlWorker(context: Context) : SQLiteOpenHelper(context,Constants.DATABASE_NAME,null,Constants.SCHEMA)
{
    companion object {
        var id = "_id"
        var title = "title"
        var create_date = "create_date"
        var update_date = "update_date"
        var note = "note"

        fun dateToString(date:Date) : String
        {
            var formater = SimpleDateFormat("yyyy-MM-dd")
            return formater.format(date)
        }
        fun stringToDate(date:String) : Date
        {
            var formater = SimpleDateFormat("yyyy-MM-dd")
            return formater.parse(date)
        }
    }
    var db:SQLiteDatabase? = null
        get() = field
        set(value) {
            field = value
            init()
        }

    override fun onCreate(db: SQLiteDatabase?) {
        this.db = db
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    private fun init()
    {
        var command = "CREATE TABLE IF NOT EXISTS ${Constants.TABLE_NAME} ($id INTEGER PRIMARY KEY AUTOINCREMENT," +
                             "$title TEXT, $note TEXT, $create_date DATE, $update_date DATE );"
        db?.execSQL(command)
    }
    fun insertNote(nt:Note)
    {
        var content = ContentValues()
        content.put(title,nt.title)
        content.put(Companion.note,nt.note)
        content.put(create_date,dateToString(nt.create_date))
        content.put(update_date,dateToString(nt.update_date))
        var i = db?.insert(Constants.TABLE_NAME,null,content)
        println()
    }
    fun update(nt:Note)
    {
        var content = ContentValues()
        content.put(title,nt.title)
        content.put(note,nt.note)
        content.put(create_date,dateToString(nt.create_date))
        content.put(update_date,dateToString(nt.update_date))
        db?.update(Constants.TABLE_NAME,content,"$id = ${nt.id}",null)
    }

    fun delete(note_id:Int)
    {
        db?.delete(Constants.TABLE_NAME,"${id} = ${note_id}",null)
    }
    fun selectAll() : ArrayList<Note>?
    {
        var command = "SELECT * FROM ${Constants.TABLE_NAME}"
        var cursor = db?.rawQuery(command,null)
        var notes:ArrayList<Note> = ArrayList<Note>()
        if (cursor?.moveToFirst()!!) {
            do {
                var note = Note()
                note.id = cursor?.getInt(0)!!
                note.title = cursor?.getString(1)
                note.note = cursor?.getString(2)
                note.create_date = stringToDate(cursor?.getString(3))
                note.update_date = stringToDate(cursor?.getString(4))
                notes.add(note)
            } while (cursor?.moveToNext())
        }
        cursor?.close()
        return notes
    }
    fun dispose()
    {
        db?.close()
    }

}
