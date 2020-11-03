package com.example.broadcastreceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //延迟申明TimeChangeReceive的一个实例
    lateinit var timeChangeReceiver: TimeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intentFilter = IntentFilter()
        //接受时间变化的广播信息
        intentFilter.addAction("android.intent.action.TIME_TICK")
        //实例化
        timeChangeReceiver = TimeChangeReceiver()
        //动态注册广播
        registerReceiver(timeChangeReceiver, intentFilter)
    }

    /**应用摧毁时注销timeChangeReceive的注册*/
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)
    }

    /**定义继承与Broadcast的内部类TimeChangeReceive*/
    inner class TimeChangeReceiver : BroadcastReceiver() {
        @SuppressLint("WrongConstant", "ShowToast")
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context,"time has been changed", 10000).show()
        }
    }
}