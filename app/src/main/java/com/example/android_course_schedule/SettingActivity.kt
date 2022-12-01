package com.example.android_course_schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)



        // get user info
//        user?.let {
//            // Name, email address, and profile photo Url
//            val name = user.displayName
//            val email = user.email
//
//            // Check if user's email is verified
//            val emailVerified = user.isEmailVerified
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getToken() instead.
//            val uid = user.uid
//            Log.d("test", "onCreate: $name , $email, $emailVerified, $uid")
//        }






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




        val btnUpdate = findViewById<Button>(R.id.bt_update_setting)
        val btnOut = findViewById<Button>(R.id.bt_logout_setting)

        btnUpdate.setOnClickListener {
            jumpFun(SettingUpdateActivity::class.java)
        }

        btnOut.setOnClickListener {
            Firebase.auth.signOut()
            finishAffinity()
            jumpFun(MainActivity::class.java)

        }
    }

    override fun onResume() {
        super.onResume()

        val user = Firebase.auth.currentUser

        // get user profile
        user?.let {
            for (profile in it.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId

                // UID specific to the provider
                val uid = profile.uid

                // Name, email address, and profile photo Url
                val email = profile.email
                var name = profile.displayName
                if(name == null || name == ""){
//                    Log.d("test", "onResume: in!!!!!!!!!!!!!!!!!!!")
                    name = email
                }

//                val photoUrl = profile.photoUrl
                val isEmailVerified = profile.isEmailVerified
                val phoneNumber = profile.phoneNumber
                Log.d("test", "onCreate: providerId:$providerId, name:$name , $email, $phoneNumber, $uid")

                findViewById<TextView>(R.id.tv_uid_setting).text = "Email: "+email
                findViewById<TextView>(R.id.tv_username_setting).text = "Name: "+name
//                findViewById<TextView>(R.id.tv_isEmailVerified_setting).text = "IsEmailVerified: "+isEmailVerified.toString()
            }
        }
    }


    private fun jumpFun(toActivity:Class<*>) {
        val intent = Intent(this, toActivity)
        startActivity(intent)
    }
}