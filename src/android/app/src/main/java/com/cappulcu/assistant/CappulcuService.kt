package com.cappulcu.assistant

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class CappulcuService : Service() {
    
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("cappulcu", "Çapulcu", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, "cappulcu")
            .setContentTitle("Çapulcu aktif")
            .setContentText("'Çapulcu' dediğinde seni duyacağım")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .build()
        startForeground(1001, notification)
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
}
