package com.example.network

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var webView:WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //设置有跳转超链接的SpannableString
        val str = SpannableString("打开百度")

        //设置颜色
        val foregroundColorSpan = ForegroundColorSpan(Color.parseColor("#0000FF"))
        str.setSpan(foregroundColorSpan, 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        //下划线
        val underlineSpan = UnderlineSpan()
        str.setSpan(underlineSpan, 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        //给文字"百度"添加点击事件
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            @SuppressLint("SetJavaScriptEnabled")
            override fun onClick(widget: View) {
                webView = object : WebView(this@MainActivity){
                    override fun destroy() {
                        finishAffinity()
                        //重新进入主界面
                        Toast.makeText(this@MainActivity, "返回主界面", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, MainActivity::class.java)
                        startActivity(intent)
                        super.destroy()
                    }
                }
                setContentView(webView)
                webView.settings.javaScriptEnabled = true
                webView.webViewClient = WebViewClient()
                // webView.webChromeClient = WebChromeClient()
                webView.loadUrl("http://www.baidu.com")
                Toast.makeText(this@MainActivity, "打开百度", Toast.LENGTH_SHORT).show()
            }
        }
        str.setSpan(clickableSpan, 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        //将SpannableString添加至textView
        textView.text = str
        textView.movementMethod = LinkMovementMethod.getInstance() //不设置则没有点击事件
        textView.highlightColor = Color.TRANSPARENT //设置点击后的颜色为透明
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        //WebView中返回上一浏览器页面
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        else{
            super.onBackPressed()
        }
        return super.onKeyDown(keyCode, event)
    }
    override fun onDestroy() {
        if (webView != null) {
            (webView.parent as ViewGroup).removeView(webView)
            webView.destroy()
        }
        super.onDestroy()
    }
}