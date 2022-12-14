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


class HomeActivity : AppCompatActivity() {
    private lateinit var uid:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        uid = Firebase.auth.uid.toString()

        val bt_search = findViewById<Button>(R.id.btn_search_home)
        val bt_map = findViewById<Button>(R.id.btn_map)
        val bt_setting = findViewById<Button>(R.id.btn_setting)

        bt_setting.setOnClickListener {
            jumpFun(SettingActivity::class.java)
        }
        bt_search.setOnClickListener {
            jumpFun(SearchActivity::class.java)
        }
        bt_map.setOnClickListener {
            jumpFun(MapPointer::class.java)
        }


        // recyclerView
        loadData()

        // left data
        val leftItemsList = ArrayList<Items>()
        val timeList = listOf("7:25\nto\n8:15","8:30\nto\n9:20","9:35\nto\n10:25","10:40\nto\n11:30","11:45\nto\n12:35","12:50\nto\n13:40","13:55\nto\n14:45","15:00\nto\n15:50","16:05\nto\n16:55","17:10\nto\n18:00","18:15\nto\n19:05","19:20\nto\n20:10","20:20\nto\n21:10","21:20\nto\n22:10")
        for (i in timeList) {
            val items = Items(1,i)
            leftItemsList.add(items)
        }

        // top data
        val dayList = listOf("Mon", "Tues", "Wed", "Thurs","Fri", "Sat", "Sun")
        val topItemsList = ArrayList<Items>()
        for (i in dayList) {
            val items = Items(1,i)
            topItemsList.add(items)
        }

//        initRecyclerView(itemsList,R.id.recycleView,true,GridLayoutManager(applicationContext,7))

        //left
        initBarRecyclerView(leftItemsList,R.id.left_recyclerView,GridLayoutManager(applicationContext,1))
        //top
        initBarRecyclerView(topItemsList,R.id.top_recyclerView,GridLayoutManager(applicationContext,7))

        // sync roll
        val scrollView0 = findViewById<SyncHorizontalScrollView>(R.id.top_scroll)
        val scrollView1 = findViewById<SyncHorizontalScrollView>(R.id.main_scroll)
        scrollView0.setScrollView(scrollView1)
        scrollView1.setScrollView(scrollView0)

    }

    private fun initRecyclerView(itemsList : ArrayList<Items>, viewID : Int, layoutManager: RecyclerView.LayoutManager){
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
                Log.d("test","position: $position")
                jumpFun(newClassActivity::class.java,position)
            }
        })

    }

    private fun initBarRecyclerView(itemsList : ArrayList<Items>, viewID : Int, layoutManager: RecyclerView.LayoutManager){
        Log.d("test","id: $viewID")
        val recyclerView = findViewById<RecyclerView>(viewID)

        val adapter = RecyclerViewAdapterForBar(itemsList)
//        val layoutManager = GridLayoutManager(applicationContext,7)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

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
//                Log.d("test", "onDataChange: starting loading!!!!!!!!!!!!!!!!!!"+snapshot)
                val value = snapshot.getValue<Map<String, Any>>()
//                val value = snapshot.value as Map<String, Any>?
//                Log.d("test", "onDataChange-->value: $value")
//                if (value != null) {
                    val itemsList = ArrayList<Items>()
                    // ############## deal recycler view ####################
                    loadMapData(value,itemsList)
                    initRecyclerView(itemsList,R.id.recycleView,GridLayoutManager(applicationContext,7))
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
                val item = Items(2,courseName, location)
                itemsList.add(item)
                Log.d("test", "loadMapData1111: $item ${itemsList[i]}")
            } else {
                val item = Items(0, "")
                itemsList.add(item)
            }
        }
    }

}