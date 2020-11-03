package com.example.threadtest

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val updateText = 1
    val handler = @SuppressLint("HandlerLeak")
    object : Handler(){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                updateText -> textView.text = "Nice to meet you"
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val favourate = arrayOf("唱歌", "跳舞", "写作业")
        val isSelected = booleanArrayOf(false, false, true)
        with(AlertDialog.Builder(this)) {
            setTitle("选择兴趣")
            setMultiChoiceItems(
                favourate,
                isSelected,
                DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked -> show() })
            setPositiveButton("取消") { dialog, which -> show() }
            setNegativeButton("确定") { dialog, which -> show() }
            create()
        }.show()

        changeTextButton.setOnClickListener {
            thread {
               val msg = Message()
                msg.what = updateText
                handler.sendMessage(msg)
            }
        }
    }
}

class MyAsyncTask : AsyncTask<Unit, Int, Boolean>(){
    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Unit?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
    }
}