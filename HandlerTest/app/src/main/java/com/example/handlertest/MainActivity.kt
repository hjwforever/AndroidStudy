package com.example.handlertest

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    /**定义全局的进度条及进度变量*/
    private lateinit var progressDialog : ProgressDialog
    private var progressVar : Int = 0

    /**自定义Handler*/
    private var handler : Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what >= progressDialog.max) Toast.makeText(this@MainActivity,"下载已完成",Toast.LENGTH_SHORT).show()
            progressDialog.setProgress(msg.what)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**初始化进度条的全局设置*/
        initProgressDialog()
    }

    /**初始化进度条的全局设置*/
    fun initProgressDialog(){
        progressDialog =  ProgressDialog(this)
        progressDialog.setTitle("任务正在执行中")
        progressDialog.setMessage("任务正在执行中，敬请等待...")
        //设置对话进度条样式为水平
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false)
        progressDialog.setMax(202);
    }

    /**Button1点击事件,自定义的AsyncTask任务,使用AsyncTask的形式更新进度条*/
    fun showProgressDialogByAsyncTask(view : View) {
        val task : MyAsyncTask =MyAsyncTask(this)
        task.execute()
        Toast.makeText(this,"开始下载 by AsyncTask",Toast.LENGTH_SHORT).show()
    }

    /**Button2点击事件,使用Handler及Message的形式更新UI*/
    fun showProgressDialogByHandler(view : View) {
        progressDialog.show()

        Toast.makeText(this,"开始下载 by Handler",Toast.LENGTH_SHORT).show()
        thread {
            progressVar = 0
            while (progressVar < 202) {
                try {
                    progressVar += progressVar%8 + 1
                    handler.sendEmptyMessage(if (progressVar<202) progressVar else 202)
                    Thread.sleep(100)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            progressDialog.cancel()
        }

    }

    /**内部类,自定义AsyncTas任务，更新进度条*/
    @SuppressLint("StaticFieldLeak")
    inner class MyAsyncTask internal constructor(context: MainActivity) : AsyncTask<Unit, Int, Int?>() {
        private var string: String? = null
        private val activityReference: WeakReference<MainActivity> = WeakReference(context)

        /**线程任务进行前进行初始化*/
        override fun onPreExecute() {
            progressDialog.show()
            progressVar = 0
            progressDialog.progress = 0
        }

        /**后台模拟下载任务的进度增长*/
        override fun doInBackground(vararg params: Unit?): Int? {
            activityReference.get()
            try {
                while (progressVar < progressDialog.max ) {
                    progressVar += progressVar % 5 + 5
                    Thread.sleep(100)
                    publishProgress()  //发送信号，使进度条及显示数据更新
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return progressVar
        }

        /**线程任务结束后将进度条取消显示*/
        override fun onPostExecute(result: Int?) {
            progressDialog.cancel()
            Toast.makeText(activityReference.get(),"下载任务已完成",Toast.LENGTH_SHORT).show()
        }

        /**更新模拟下载任务的进度条，进度百分比和下载文件数*/
        override fun onProgressUpdate(vararg value: Int?) {
            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return

            //进度数据更新
            if (progressVar>progressDialog.max) progressVar=progressDialog.max
            progressDialog.progress = progressVar
        }
    }
}