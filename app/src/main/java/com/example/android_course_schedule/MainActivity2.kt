package com.example.android_course_schedule


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val bt = findViewById<Button>(R.id.btn_back)

        bt.setOnClickListener {
            finish()
        }

        // recyclerView
        val itemsList = ArrayList<Items>()
        for (i in 0..104) {
            val items = Items("", "")
            itemsList.add(items)
        }
        Log.d("test", itemsList.toString())

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

        initRecyclerView(itemsList,R.id.recycleView,true,GridLayoutManager(applicationContext,7))

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
                    jumpFun()
                }
            }
        })

    }

    private fun jumpFun() {
        val intent = Intent(this, newClass::class.java)
        val msg = "message"
        intent.putExtra("msg",msg)
        startActivity(intent)

    }
}