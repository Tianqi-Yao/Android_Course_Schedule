package com.example.android_course_schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val TAG: String = "test"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        val bt = findViewById<Button>(R.id.btn_login)
        val bt_signup = findViewById<Button>(R.id.btn_signup)

        bt_signup.setOnClickListener {
            jumpFun(newUserActivity::class.java,"")
        }

        bt.setOnClickListener {
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                Log.d(TAG, "onCreate: "+username+password)
                emailSignIn(username,password)
            }
            else{
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.d(TAG, "onStart: user is signed in. go!!!!!!!!!")
            updateUI(currentUser);
        }
    }

    private fun jumpFun(toActivity:Class<*>,uid:String) {
        val intent = Intent(this, toActivity)
        intent.putExtra("uid",uid)
        startActivity(intent)
    }

    private fun emailSignIn(email:String, password:String){
        Log.d(TAG, "emailSignIn: "+email +" " + password)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }

    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null){
            Log.d(TAG, "uid: ${user.uid}")
            jumpFun(HomeActivity::class.java,user.uid)
        }
    }
}