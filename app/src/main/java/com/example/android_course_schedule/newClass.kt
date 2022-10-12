package com.example.android_course_schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class newClass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_class)

        val bt = findViewById<Button>(R.id.btn_back_newclass)

        bt.setOnClickListener {
            finish()
        }
    }


}