package uz.zokirbekov.todo.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.recivers.TimeReciver

class AlarmWorker(var context: Context) {
    var alarmManager:AlarmManager? = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
    fun setAlarm(nt: Note)
    {
        var intent = Intent(context, TimeReciver::class.java)
        intent.putExtra("title",nt.note)
        var pendingIntent = PendingIntent.getBroadcast(context,0,intent,0)
        //alarmManager?.set
    }
}