package com.example.notificationtest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mNotificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        //定义一个PendingIntent点击Notification后启动一个Activity
        val it = Intent(this, AnotherActivity::class.java)
        val pit = PendingIntent.getActivity(this, 0, it, 0)


        val button : Button = findViewById(R.id.button)
        button.setOnClickListener {
            Toast.makeText(this, "ssss", Toast.LENGTH_SHORT).show()
            sendNotification("s","ss","sss","sssssss",1)

        }
//
//
//            var notiBuilder = NotificationCompat.Builder(this)
////            var btm = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
////            val notification : Notification = Notification()
//            notiBuilder
////            .setSmallIcon(R.mipmap.ic_launcher) //设置小图
//
//                    .setContentTitle("通知")//标题
//
//                    .setContentText("消息内容")//消息内容
////                    .setLargeIcon(btm) //设置大图
//
//                    .setAutoCancel(true)//点击消息后自动清除通知
//
////                    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
////                    .setContentIntent(pit)
////                    .setPriority(Notification.PRIORITY_MAX) //优先级
////
////                    .setDefaults(Notification.DEFAULT_ALL)//设置默认
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                mNotificationManager.createNotificationChannel(
//                        NotificationChannel(
//                                packageName, "通知栏",
//                                NotificationManager.IMPORTANCE_DEFAULT
//                        )
//                )
//            }
//
////            var resultIntent = Intent(applicationContext, AnotherActivity::class.java) //添加跳转目标
////            //传递信息
////            resultIntent.putExtra("content", "通知栏信息")
////            var resultPI = PendingIntent.getActivity(
////                    getApplication(),
////                    100,
////                    resultIntent,
////                    PendingIntent.FLAG_CANCEL_CURRENT
////            )
////            notiBuilder.setContentIntent(resultPI)
//
//            mNotificationManager.notify(1, notiBuilder.build());//第一个参数为notification  的唯一标识
//
//
//        }

    }

    fun sendNotification(channel_id:String ,channle_name:String,nTitle:String,nContentText:String,id:Int){

        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notification: Notification? = null
//val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com/"))
//val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//如需链接到网站使用注释代码
        val intent = Intent()
        intent.setClass(this@MainActivity, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val mChannel = NotificationChannel(channel_id, channle_name, NotificationManager.IMPORTANCE_LOW)

                    notificationManager.createNotificationChannel(mChannel)
                    notification = Notification.Builder(this)
                            .setChannelId(channel_id)
                            .setContentTitle(nTitle)
                            .setContentText(nContentText)
                            .setContentIntent(pendingIntent)
                            .setSmallIcon(R.drawable.house).build()

                } else {
                    val notificationBuilder = NotificationCompat.Builder(this)
                            .setContentTitle(nTitle)
                            .setContentText(nContentText)
                            .setSmallIcon(R.drawable.house)
                            .setOngoing(true)
//.setChannel(id)//无效
                    notification = notificationBuilder.build()
                }
                        notificationManager.notify(id, notification)
    }

}