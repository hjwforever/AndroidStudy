package com.example.servicetest

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast

class MyIntentService : IntentService("MyIntentService") {

    private val handler = Handler(Looper.getMainLooper())

    override fun onBind(intent: Intent?): IBinder? {
        // 通过handler.post实现Toast提示
        handler.post { Toast.makeText(this, "MyIntentService onBind\nName: $name\nID: $ID", Toast.LENGTH_SHORT).show() }
        return super.onBind(intent)
    }

    override fun onHandleIntent(p0: Intent?) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyIntentService", "onDestroy executed")
    }

    companion object {
        private const val TAG = "MyIntentService"
        private const val name = "黄俊雯"
        private const val ID = "18301127"
    }
}