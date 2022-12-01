package com.example.android_course_schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.android_course_schedule.place.Place
import com.example.android_course_schedule.place.PlacesReader
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class newClassActivity : AppCompatActivity() {
    private lateinit var location: String
    private val TAG: String = "test"

    private val places: List<Place> by lazy {
        PlacesReader(this).read()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_class)

        val btnBack = findViewById<Button>(R.id.btn_back_newclass)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)

        val position  = intent.getIntExtra("position",-1)
        val uid  = intent.getStringExtra("uid")

        val usernameET = findViewById<EditText>(R.id.et_coursename)
        val locationSP = findViewById<Spinner>(R.id.spinner_location)

//        Log.d(TAG, "onCreate22->places1111: ${places}")
        val locationNameList = ArrayList<String>()
        places.forEach { locationNameList.add(it.name) }
//        Log.d(TAG, "onCreate22: $locationNameList")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locationNameList)
        locationSP.adapter = adapter

        locationSP.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                Toast.makeText(baseContext, "choose" + locationNameList[pos], Toast.LENGTH_SHORT).show()
                location = locationNameList[pos]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnSubmit.setOnClickListener {
            val username = usernameET.text.toString()
//            val location = locationET.text.toString()
            writeData(position, username, location, uid!!)
            finish()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }


    private fun writeData(position:Int, courseName: String, location: String,uid:String){
        Log.d("test","start writing data")
        // Write a message to the database
        val database = Firebase.database
//        val myRef = database.reference.child("message").child("position")
//        myRef.setValue("1")
        val myRef = database.reference.child(uid).child("position")
//        val key = myRef.push().key
//        val classInfo = mutableMapOf<String, String>("中国" to "China", "英国" to "England")
        Log.d("test", "courseName: $courseName location: $location ")
        val courseInfo =mutableMapOf<String, String>().apply {
            this["courseName"] = courseName
            this["location"] = location
        }
//        myRef.setValue(classInfo)
        val childUpdates = mutableMapOf<String, Any>(
            "/p$position" to courseInfo,
        )
        Log.d("test","$position",)
        myRef.updateChildren(childUpdates)

    }

}