package com.example.asynctasktest

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    var progressVar : Int = 0  //进度条数值

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //按钮监听器
        button.setOnClickListener {
            progressVar = 0
            //创建并运行异步线程任务
            val task = MyAsyncTask(this)
            task.execute()
            Toast.makeText(this,"下载任务开始",Toast.LENGTH_SHORT).show()
        }

    }

    inner class MyAsyncTask internal constructor(context: MainActivity) : AsyncTask<Unit, Int, Int?>() {
        private var string: String? = null
        private val activityReference: WeakReference<MainActivity> = WeakReference(context)
        val progressBar = ProgressBar(activityReference.get(),null,0,R.style.Widget_AppCompat_ProgressBar_Horizontal)
        val progressDialog = with(AlertDialog.Builder(activityReference.get())) {
            setTitle("任务正在执行中")
            setMessage("任务正在执行中，敬请等待...")
            setView(progressBar)
//          setNegativeButton("NegativeButton",null)
            setNeutralButton("0%",null)
            setPositiveButton("0/202",null)
            setCancelable(false)
            create()
        }
//            val progressDialog  = with(ProgressDialog(activityReference.get())){
//                setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
//                setTitle("任务正在执行中")
//                setMessage("任务正在执行中，敬请等待...")
//                max = 202
//                show()
//            }

        /**线程任务进行前进行初始化*/
        override fun onPreExecute() {
            progressBar.max = 100
            progressDialog.show()
            //按钮不可点击
            progressDialog.getButton(DialogInterface.BUTTON_NEUTRAL).isClickable = false
            progressDialog.getButton(DialogInterface.BUTTON_POSITIVE).isClickable = false
            //按钮字体颜色为黑色
            progressDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#000000"));
            progressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
        }

        /**后台模拟下载任务的进度增长*/
        override fun doInBackground(vararg params: Unit?): Int? {
            val activity = activityReference.get()
            try {
                while (activity?.progressVar!! < 100 ) {
                    activity.progressVar = activity.progressVar + activity.progressVar%2 + 1
                    Thread.sleep(100)
                    publishProgress()  //发送信号，使进度条及显示数据更新
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return activity?.progressVar
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

            var process:Int = activity.progressVar
            if (process>100) process = 100
            val num:Int = (process*2.02).roundToInt()

            //进度数据更新
            progressDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setText("$process%")
            progressBar.progress = activity.progressVar
            progressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText("$num/202")
        }
    }



}

