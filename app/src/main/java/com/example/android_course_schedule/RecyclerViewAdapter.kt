package com.example.android_course_schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter internal constructor(private val itemsList: List<Items>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.courseName.text = item.courseName
        holder.courseLocation.text = item.courseLocation

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