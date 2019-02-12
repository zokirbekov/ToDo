package uz.zokirbekov.todo.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.models.Schedule
import uz.zokirbekov.todo.recivers.TimeReciver
import java.util.*

class AlarmWorker(var context: Context) {
    var alarmManager:AlarmManager? = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
    fun setAlarm(sch: Schedule)
    {
        var intent = Intent(context, TimeReciver::class.java)
        intent.putExtra("title",sch.title)
        var pendingIntent = PendingIntent.getBroadcast(context,sch.id,intent,0)
        var i = SystemClock.elapsedRealtime()
        var j = sch.time.time
        var k = System.currentTimeMillis()

        var cur_cal = GregorianCalendar.getInstance()
        cur_cal.timeInMillis = sch.time.time
        cur_cal.set(GregorianCalendar.SECOND,0)
        cur_cal.set(GregorianCalendar.MILLISECOND,0)

        alarmManager?.set(AlarmManager.RTC_WAKEUP,cur_cal.timeInMillis,pendingIntent)
    }

    fun cancelAlarm()
    {

    }
}