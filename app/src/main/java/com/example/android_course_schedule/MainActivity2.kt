package com.example.android_course_schedule


import android.content.Context
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val bt = findViewById<Button>(R.id.btn_back)
        val bt_map = findViewById<Button>(R.id.btn_map)

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
        for (i in 6..20) {
            val items = Items("$i o'clock", "")
            leftItemsList.add(items)
        }

        // top data
        val topItemsList = ArrayList<Items>()
        for (i in 1..7) {
            val items = Items("day $i", "")
            topItemsList.add(items)
        }

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
        startActivity(intent)

    }

    private fun loadData() {
        // Read from the database
        val database = Firebase.database
        val myRef = database.reference.child("position")
        Log.d("test","myRef: $myRef")
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<Map<String, Any>>()
                if (value != null) {
                    val itemsList = ArrayList<Items>()
                    loadMapData(value,itemsList)
                    initRecyclerView(itemsList,R.id.recycleView,true,GridLayoutManager(applicationContext,7))
                }
                else{
                    Log.d("test","do not have data from position")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("test", "Failed to read value.", error.toException())
            }

        })
    }


    private fun loadMapData(value: Map<String, Any>,itemsList : ArrayList<Items>) {

        for (i in 0..104) {
            Log.d("test", "map value is: $i  ${value["$i"]}")
            val courseInfo: Any? = value["$i"]
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