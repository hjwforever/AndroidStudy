package com.example.datamanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        /**Load，使用SharedPreferences进行读取*/
        button.setOnClickListener {
            val sharedPreferences = getSharedPreferences("userInfo",0)
            val username = sharedPreferences.getString("username","黄俊雯")
            val password = sharedPreferences.getString("password","18301127")
            textView.text = username
            textView2.text = password
            Log.d(tag,"读取username:${username} 和password: $password")
            Toast.makeText(this, "读取username和password", Toast.LENGTH_SHORT).show()
        }

        button2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Log.d(tag, "从${tag}跳转至$nexttag")
            Toast.makeText(this, "从${tag}跳转至$nexttag", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val tag = "SecondActivity"
        private const val nexttag = "MainActivity"
    }
}