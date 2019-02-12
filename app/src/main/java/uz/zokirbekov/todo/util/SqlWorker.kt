package uz.zokirbekov.todo.util

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.Constants
import uz.zokirbekov.todo.models.Schedule
import java.sql.Timestamp
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
        var time = "time"

        var LastScheduleId : Int = 0

        fun dateToString(date:Date) : String
        {
            val formater = SimpleDateFormat("yyyy-MM-dd")
            return formater.format(date)
        }
        fun stringToDate(date:String) : Date
        {
            val formater = SimpleDateFormat("yyyy-MM-dd")
            return formater.parse(date)
        }
        fun timestampToString(date:Date):String
        {
            val formater = SimpleDateFormat("yyyy-MM-dd HH:mm")
            return formater.format(date)
        }
        fun stringToTimestamp(str:String):Date
        {
            val formater = SimpleDateFormat("yyyy-MM-dd HH:mm")
            return formater.parse(str)
        }
        fun timeToString(date:Date) : String
        {
            val formater = SimpleDateFormat("HH:mm")
            return formater.format(date)
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
        var createNoteTable = "CREATE TABLE IF NOT EXISTS ${Constants.NOTE_TABLE_NAME} ($id INTEGER PRIMARY KEY AUTOINCREMENT," +
                             "$title TEXT, $note TEXT, $create_date DATE, $update_date DATE );"
        var createScheduleTable =  "CREATE TABLE IF NOT EXISTS ${Constants.SCHEDULE_TABLE_NAME} ($id INTEGER PRIMARY KEY," +
                             "$title TEXT, $time TIMESTAMP, $create_date DATE, $update_date DATE );"
        db?.execSQL(createNoteTable)
        db?.execSQL(createScheduleTable)
    }
    fun insertNote(nt:Note)
    {
        var content = ContentValues()
        content.put(title,nt.title)
        content.put(note,nt.note)
        content.put(create_date,dateToString(nt.create_date))
        content.put(update_date,dateToString(nt.update_date))
        var i = db?.insert(Constants.NOTE_TABLE_NAME,null,content)
        println()
    }
    fun updateNote(nt:Note)
    {
        var content = ContentValues()
        content.put(title,nt.title)
        content.put(note,nt.note)
        content.put(update_date,dateToString(nt.update_date))
        db?.update(Constants.NOTE_TABLE_NAME,content,"$id = ${nt.id}",null)
    }

    fun deleteNote(id:Int)
    {
        db?.delete(Constants.NOTE_TABLE_NAME,"${SqlWorker.id} = ${id}",null)
    }
    fun selectAllNotes() : ArrayList<Note>?
    {
        var command = "SELECT * FROM ${Constants.NOTE_TABLE_NAME}"
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

    fun insertSchdule(sch:Schedule)
    {
        var content = ContentValues()
        content.put(title,sch.title)
        content.put(time, sch.time.time.toString())
        content.put(create_date,dateToString(sch.create_date))
        content.put(update_date,dateToString(sch.update_date))
        var i = db?.insert(Constants.SCHEDULE_TABLE_NAME,null,content)
    }

    fun updateSchdule(sch:Schedule)
    {
        var content = ContentValues()
        content.put(title,sch.title)
        content.put(time, sch.time.time.toString())
        content.put(update_date,dateToString(sch.update_date))
        db?.update(Constants.SCHEDULE_TABLE_NAME,content,"$id = ${sch.id}",null)
    }

    fun deleteSchdule(id:Int)
    {
        db?.delete(Constants.SCHEDULE_TABLE_NAME,"${SqlWorker.id} = ${id}",null)
    }
    fun selectAllSchdules() : ArrayList<Schedule>?
    {
        var command = "SELECT * FROM ${Constants.SCHEDULE_TABLE_NAME}"
        var cursor = db?.rawQuery(command,null)
        var schedules:ArrayList<Schedule> = ArrayList<Schedule>()
        if (cursor?.moveToFirst()!!) {
            do {
                var schedule = Schedule()
                schedule.id = cursor?.getInt(0)!!
                schedule.title = cursor?.getString(1)
                schedule.time = Timestamp(cursor?.getLong(2))
                schedule.create_date = stringToDate(cursor?.getString(3))
                schedule.update_date = stringToDate(cursor?.getString(4))
                schedules.add(schedule)
            } while (cursor?.moveToNext())
        }
        cursor?.close()
        LastScheduleId = if (schedules.isEmpty()) 0 else schedules?.last().id
        return schedules
    }

    fun dispose()
    {
        db?.close()
    }

}
