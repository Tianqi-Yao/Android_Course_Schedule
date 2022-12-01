package com.example.android_course_schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class SettingUpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_update)

        val user = Firebase.auth.currentUser


        val btnSubmit = findViewById<Button>(R.id.bt_submit_settingUpdate)
        val btnBack = findViewById<Button>(R.id.bt_back_settingUpdate)

        btnSubmit.setOnClickListener {
            val username = findViewById<EditText>(R.id.et_username_settingUpdate).text.toString()
            val profileUpdates = userProfileChangeRequest {
                displayName = username
            }

            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("test", "User profile updated.")
//                        jumpFun(SettingActivity::class.java)
                        finish()
                    }
                }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}