package com.example.datamanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**Save，使用SharedPreferences进行存储*/
        button.setOnClickListener {
            val sharedPreferences = getSharedPreferences("userInfo",0)
            val editor = sharedPreferences.edit()
            val inputusername = editTextTextPersonName.text.toString()
            val inputpassword = editTextTextPassword.text.toString()

            if (inputusername == "" || inputpassword == ""){
                Toast.makeText(this, "输入不可为空", Toast.LENGTH_SHORT).show()
            }
            else{
                editor.putString("username", inputusername)
                editor.putString("password", inputpassword)
                editor.apply()

                Log.d(tag,"存储username:${inputusername} 和password: $inputpassword")
                Toast.makeText(this, "存储username和password", Toast.LENGTH_SHORT).show()
            }
        }

        button2.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            Log.d(tag, "从${tag}跳转至$nexttag")
            Toast.makeText(this, "从${tag}跳转至$nexttag", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val tag = "MainActivity"
        private const val nexttag = "SecondActivity"
    }
}