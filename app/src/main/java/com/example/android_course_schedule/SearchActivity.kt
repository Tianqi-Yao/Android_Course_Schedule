package com.example.android_course_schedule

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.webViewClient = WebViewClient()
        val webSettings = myWebView.settings
        webSettings.javaScriptEnabled = true
        myWebView.loadUrl("https://one.uf.edu/soc")


//        val bt_back = findViewById<Button>(R.id.btn_back_search)
        val bt_home = findViewById<Button>(R.id.btn_home_search)
        val bt_setting = findViewById<Button>(R.id.btn_setting_search)
        val bt_map = findViewById<Button>(R.id.btn_map_search)

        bt_setting.setOnClickListener {
            jumpFun(SettingActivity::class.java)
        }

//        bt_back.setOnClickListener {
//            finish()
//        }

        bt_home.setOnClickListener {
            jumpFun(HomeActivity::class.java)
        }
        bt_map.setOnClickListener {
            jumpFun(MapPointer::class.java)
        }


    }

    private fun jumpFun(toActivity:Class<*>) {
        val intent = Intent(this, toActivity)
        startActivity(intent)
    }
}