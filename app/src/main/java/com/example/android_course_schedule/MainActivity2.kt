package com.example.android_course_schedule


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class MainActivity2 : AppCompatActivity() {
    private lateinit var uid:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val bt = findViewById<Button>(R.id.btn_back)
        val bt_map = findViewById<Button>(R.id.btn_map)

        uid = intent.getStringExtra("uid").toString()

        bt.setOnClickListener {
            Firebase.auth.signOut()
            finish()
        }

        bt_map.setOnClickListener {
            jumpFun(MapPointer::class.java)
        }


        // recyclerView
        loadData()

        // left data
        val leftItemsList = ArrayList<Items>()
        val timeList = ArrayList<String>(listOf("7:25~\n8:15","8:30~\n9:20","9:35~\n10:25","10:40~\n11:30","11:45~\n12:35","12:50~\n13:40","13:55~\n14:45","15:00~\n15:50","16:05~\n16:55","17:10~\n18:00","18:15~\n19:05","19:20~\n20:10","20:20~\n21:10","21:20~\n22:10"))
        for (i in timeList) {
            val items = Items("$i", "")
            leftItemsList.add(items)
        }

        // top data
        val topItemsList = ArrayList<Items>(listOf(Items("Mon", "day"),Items("Tues", "day"),Items("Wed", "day"),Items("Thurs", "day"),Items("Fri", "day"),Items("Sat", "day"),Items("Sun", "day")))


//        initRecyclerView(itemsList,R.id.recycleView,true,GridLayoutManager(applicationContext,7))

        //left
        initRecyclerView(leftItemsList,R.id.left_recyclerView,false,GridLayoutManager(applicationContext,1))
        //top
        initRecyclerView(topItemsList,R.id.top_recyclerView,false,GridLayoutManager(applicationContext,7))

        // sync roll
        val scrollView0 = findViewById<SyncHorizontalScrollView>(R.id.top_scroll)
        val scrollView1 = findViewById<SyncHorizontalScrollView>(R.id.main_scroll)
        scrollView0.setScrollView(scrollView1)
        scrollView1.setScrollView(scrollView0)

    }

    private fun initRecyclerView(itemsList : ArrayList<Items>, viewID : Int, allowedClick: Boolean, layoutManager: RecyclerView.LayoutManager){
        Log.d("test","id: $viewID")
        val recyclerView = findViewById<RecyclerView>(viewID)

        val adapter = RecyclerViewAdapter(itemsList)
//        val layoutManager = GridLayoutManager(applicationContext,7)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        //click fun

        adapter.setOnItemClickListener(object : RecyclerViewAdapter.ClickListener<Items> {
            override fun onClick(view: View?, data: Items, position: Int) {
                if (allowedClick) {
                    Log.d("test","position: $position")
                    jumpFun(newClass::class.java,position)
                }
            }
        })

    }

    private fun jumpFun(toActivity:Class<*>,position: Int = -1) {
        val intent = Intent(this, toActivity)
        intent.putExtra("position", position)
        intent.putExtra("uid", uid)
        startActivity(intent)

    }

    private fun loadData() {
        // Read from the database
        val database = Firebase.database
        val myRef = database.reference.child(uid).child("position")
        Log.d("test","myRef: $myRef")
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d("test", "onDataChange: starting loading!!!!!!!!!!!!!!!!!!"+snapshot)
                val value = snapshot.getValue<Map<String, Any>>()
//                val value = snapshot.value as Map<String, Any>?
                Log.d("test", "onDataChange-->value: $value")
//                if (value != null) {
                    val itemsList = ArrayList<Items>()
                    loadMapData(value,itemsList)
                    initRecyclerView(itemsList,R.id.recycleView,true,GridLayoutManager(applicationContext,7))
//                }
//                else{
//                    Log.d("test","do not have data from position")
//                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("test", "Failed to read value.", error.toException())
            }

        })
    }


    private fun loadMapData(value: Map<String, Any>?, itemsList: ArrayList<Items>) {

        for (i in 0..97) {
//            Log.d("test", "map value is: $i  ${value["$i"]}")
            val courseInfo: Any? = value?.get("p$i")
            if (courseInfo is Map<*, *>) {
                val courseName: String = courseInfo["courseName"] as String
                val location: String = courseInfo["location"] as String
                val item = Items(courseName, location)
                itemsList.add(item)
                Log.d("test", "loadMapData1111: $item ${itemsList[i]}")
            } else {
                val item = Items("", "")
                itemsList.add(item)
            }
        }
    }

}