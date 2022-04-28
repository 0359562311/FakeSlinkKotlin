package com.example.fakeslink.custom_views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.example.fakeslink.R

@SuppressLint("Recycle")
class StackLoading(context: Context, val attrs: AttributeSet): ConstraintLayout(context, attrs) {
    init {
        for(i in this.children) {
            addView(i, i.layoutParams)
        }
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.StackLoading)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }
}