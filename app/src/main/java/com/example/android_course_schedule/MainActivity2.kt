package com.example.android_course_schedule


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val bt = findViewById<Button>(R.id.btn_back)

        val msg = intent.getStringExtra("msg")

        bt.text = msg

        bt.setOnClickListener {
            finish()
        }

        // recyclerView
        val itemsList = ArrayList<Items>()

        for (i in 0..100) {
            val items = Items("name$i", "FL${2000+i}")
            itemsList.add(items)
        }
        Log.d("test", itemsList.toString())

        val recyclerView = findViewById<RecyclerView>(R.id.recycleView)

        val adapter = RecyclerViewAdapter(itemsList)
        val layoutManager = GridLayoutManager(applicationContext,7)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
//        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)

        //click fun
        adapter.setOnItemClickListener(object : RecyclerViewAdapter.ClickListener<Items> {
            override fun onClick(view: View?, data: Items, position: Int) {
                Toast.makeText(applicationContext, "Position = $position  data = ${data.courseName}", Toast.LENGTH_LONG).show()
            }
        })

    }

//    private fun jumpFun() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//    }
}