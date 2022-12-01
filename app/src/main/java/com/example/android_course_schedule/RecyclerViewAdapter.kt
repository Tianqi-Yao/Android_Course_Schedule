package com.example.android_course_schedule

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter internal constructor(private val itemsList: List<Items>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
//        Log.d("test", "onCreateViewHolder viewID11: ${view.findViewById<TextView>(R.id.tv_courseName)}")
//        view.findViewById<TextView>(R.id.tv_courseName).setBackgroundColor(Color.parseColor("#f0ebe5"))
//        view.findViewById<TextView>(R.id.tv_courseLocation).setBackgroundColor(Color.parseColor("#f0ebe5"))
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
//        Log.d("test", "onBindViewHolder->item: $item")

        val courseName = item.courseName
        holder.courseName.text = courseName
        holder.courseLocation.text = item.courseDetail1

        val courseNameHash = courseName.hashCode()
        Log.d("test", "onBindViewHolder courseNameHash1: $courseNameHash,  ${courseNameHash%10}")

        val colorList = listOf(
            "#f0ebe5",
            "#ead0d1",
            "#eee5f8",
            "#faead3",
            "#f2ca97",
            "#c5c9a9",
            "#ece6e6",
            "#f7bc99",
            "#858fac",
            "#ab9796")

        if (courseNameHash != 0){
            holder.itemView.findViewById<TextView>(R.id.tv_courseName).setBackgroundColor(Color.parseColor(colorList[courseNameHash%10]))
            holder.itemView.findViewById<TextView>(R.id.tv_courseLocation).setBackgroundColor(Color.parseColor(colorList[courseNameHash%10]))
        }
        //click fun
        holder.itemView.setOnClickListener{
            view -> clickListener!!.onClick(view,item,position)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var courseName: TextView = itemView.findViewById(R.id.tv_courseName)
        var courseLocation: TextView = itemView.findViewById(R.id.tv_courseLocation)


    }

    // click fun
    interface ClickListener<T> {
        fun onClick(view: View?, data: T, position: Int)
    }

    private var clickListener: ClickListener<Items>? = null

    fun setOnItemClickListener(clickListener: ClickListener<Items>) {
        this.clickListener = clickListener
    }
}