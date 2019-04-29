package uz.zokirbekov.todo.recivers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.support.v4.app.NotificationCompat
import uz.zokirbekov.todo.MainActivity
import uz.zokirbekov.todo.R


class TimeReciver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var notManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //var pm = context?.getSystemService(PowerManager::class.java) as PowerManager
        var notification: NotificationCompat.Builder

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var nChannel = notManager?.getNotificationChannel("1234")
            if (nChannel == null) {
                nChannel = NotificationChannel("1234", "Test", NotificationManager.IMPORTANCE_HIGH)
                nChannel.enableVibration(true)
                notManager.createNotificationChannel(nChannel)
            }
        }
        notification = NotificationCompat.Builder(context!!,"1234")
        notification.setAutoCancel(true)
                .setSmallIcon(R.drawable.delete)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentText(intent?.extras?.getString("title"))
                .setContentTitle("Remember")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        notManager.notify(1,notification.build())
    }
}