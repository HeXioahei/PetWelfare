package com.example.petwelfare.myview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.contracts.contract

class MyLinearLayout : LinearLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    private val expendBtn = TextView(context).apply {
        text = "展示更多回复"
    }
    val expendBtnParams = LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT
    ).apply {
        gravity = Gravity.BOTTOM and Gravity.START
    }

    private val retractBtn = TextView(context).apply {
        text = "收起回复"
    }
    val retractBtnParams = LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT
    ).apply {
        gravity = Gravity.BOTTOM and Gravity.END
    }

    private fun init(context: Context) {
        addView(expendBtn)
        addView(retractBtn)
    }


}