package com.example.android_course_schedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class newUserActivity : AppCompatActivity() {
    private val TAG: String = "test"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        auth = Firebase.auth

        val bt = findViewById<Button>(R.id.signup_btn_submit)
        val bt_signup = findViewById<Button>(R.id.signup_btn_back)

        bt_signup.setOnClickListener {
            finish()
        }

        bt.setOnClickListener {
            val username = findViewById<EditText>(R.id.signup_et_username).text.toString()
            val password = findViewById<EditText>(R.id.signup_et_password).text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                Log.d(TAG, "onCreate: "+username+password)
                emailSignUp(username,password)
            }
            else{
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun emailSignUp(email:String, password:String){
        Log.d(TAG, "emailSignIn: "+email +" " + password)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

//                    user!!.sendEmailVerification()
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                Log.d(TAG, "Email sent.")
//                            }
//                        }

                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    val username = findViewById<EditText>(R.id.signup_et_username)
                    val password = findViewById<EditText>(R.id.signup_et_password)
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        password.setError(getString(R.string.error_weak_password))
                        password.requestFocus()
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        username.setError(getString(R.string.error_invalid_email))
                        username.requestFocus()
                    } catch (e: FirebaseAuthUserCollisionException) {
                        username.setError(getString(R.string.error_user_exists))
                        username.requestFocus()
                    } catch (e: Exception) {
                        Log.e(TAG, e.message!!)
                    }
                    Toast.makeText(baseContext, "Authentication failed.${task}",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    private fun updateUI(user: FirebaseUser?) {
        if (user != null){
            Log.d(TAG, "uid: ${user.uid}")
            jumpFun(MainActivity::class.java)
        }
    }

    private fun jumpFun(toActivity:Class<*>) {
        val intent = Intent(this, toActivity)
        startActivity(intent)
    }
}