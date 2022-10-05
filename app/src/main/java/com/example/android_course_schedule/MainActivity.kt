package com.example.android_course_schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bt = findViewById<Button>(R.id.btn_login)

        bt.setOnClickListener {
            Log.d("test", "onclick btn")
            println("this is print test")
            Toast.makeText(this, "this is toast test", Toast.LENGTH_LONG).show()
            jumpFun()
        }


    }

    private fun jumpFun() {
        val intent = Intent(this, MainActivity2::class.java)
        val msg = "message"
        intent.putExtra("msg",msg)
        startActivity(intent)

    }
}