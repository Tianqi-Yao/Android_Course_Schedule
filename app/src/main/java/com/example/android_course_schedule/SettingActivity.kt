package com.example.android_course_schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val bt_back = findViewById<Button>(R.id.btn_back_setting)
        val bt_home = findViewById<Button>(R.id.btn_home_setting)
        val bt_map = findViewById<Button>(R.id.btn_map_setting)

        bt_map.setOnClickListener {
            jumpFun(MapPointer::class.java)
        }

        bt_back.setOnClickListener {
            finish()
        }

        bt_home.setOnClickListener {
            jumpFun(MainActivity::class.java)
        }
    }
    private fun jumpFun(toActivity:Class<*>) {
        val intent = Intent(this, toActivity)
        startActivity(intent)
    }
}