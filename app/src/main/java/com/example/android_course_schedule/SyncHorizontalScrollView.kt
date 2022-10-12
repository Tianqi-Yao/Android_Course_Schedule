package com.example.android_course_schedule

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView


class SyncHorizontalScrollView : HorizontalScrollView {
    private var mView: View? = null

    constructor(context: Context?) : super(context) {

    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        mView?.scrollTo(l, t)
    }

    fun setScrollView(view: View?) {
        mView = view
    }
}