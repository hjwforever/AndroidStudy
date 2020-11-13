package com.example.servicetest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlin.concurrent.thread

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        Toast.makeText(this,"MyService onBind\nName: $name\nID: $ID", Toast.LENGTH_SHORT).show()
        return myBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(this,"MyService unBind", Toast.LENGTH_SHORT).show()
        return super.onUnbind(intent)
    }

    override fun onStart(intent: Intent?, startId: Int) {
        Toast.makeText(this,"MyService onStart", Toast.LENGTH_SHORT).show()
        super.onStart(intent, startId)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyService", "onCreate executed")

        // 显示通知
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("my_service", "前台Service通知", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this, "my_service")
                .setContentTitle("This is content Title")
                .setContentText("This is content Text")
                .setSmallIcon(R.drawable.small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.large_icon))
                .setContentIntent(pi)
                .build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand executed")
        Toast.makeText(this,"MyService onStartCommand", Toast.LENGTH_SHORT).show()
        thread {
            // 处理具体的逻辑
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"MyService onDestroy", Toast.LENGTH_SHORT).show()
        Log.d("MyService", "onDestroy executed")
    }

    // 模拟下载Binder
    private val myBinder = DownloadBinder()

    class DownloadBinder : Binder() {

        fun startDownload() {
            Log.d("MyService", "startDownload executed")
        }

        fun getProgress(): Int {
            Log.d("MyService", "getProgress executed")
            return 0
        }

    }

    companion object {
        private const val TAG = "MyService"
        private const val name = "黄俊雯"
        private const val ID = "18301127"
    }
}