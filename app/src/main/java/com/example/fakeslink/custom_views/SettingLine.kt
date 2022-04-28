package com.example.fakeslink.custom_views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.fakeslink.R

@RequiresApi(Build.VERSION_CODES.M)
class SettingLine(_context: Context?, attrs: AttributeSet):LinearLayout(_context, attrs) {
    private var _iconColor: ColorDrawable
    private var _iconOnPressedColor: ColorDrawable

    private val _black: ColorDrawable = ColorDrawable(resources.getColor(R.color.black,null))
    private val _fuzzyBlack: ColorDrawable =
        ColorDrawable(resources.getColor(R.color.fuzzy_black,null))
    private var title: AppCompatTextView
    private var icon: AppCompatImageView
    private var iconArrow: AppCompatImageView

    init {

        val v = inflate(context, R.layout.setting_line, this)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SettingLine)
        title = v.findViewById(R.id.setting_line_title)
        icon = v.findViewById(R.id.setting_line_icon)
        iconArrow = v.findViewById(R.id.setting_line_arrow_icon)
        title.text = attributes.getString(R.styleable.SettingLine_title)
        title.setTextColor(_black.color)

        icon.setImageDrawable(attributes.getDrawable(R.styleable.SettingLine_icon))
        _iconColor = ColorDrawable(attributes.getColor(R.styleable.SettingLine_iconColor, _black.color))
        _iconOnPressedColor = ColorDrawable(attributes.getColor(R.styleable.SettingLine_iconOnPressedColor, _fuzzyBlack.color))
        icon.imageTintList = ColorStateList.valueOf(_iconColor.color)
        iconArrow.imageTintList = ColorStateList.valueOf(_black.color)

        attributes.recycle()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_DOWN) {
            title.setTextColor(_fuzzyBlack.color)
            icon.imageTintList = ColorStateList.valueOf(_iconOnPressedColor.color)
            iconArrow.imageTintList = ColorStateList.valueOf(_fuzzyBlack.color)
            return true
        } else if(event?.action == MotionEvent.ACTION_UP) {
            title.setTextColor(_black.color)
            icon.imageTintList = ColorStateList.valueOf(_iconColor.color)
            iconArrow.imageTintList = ColorStateList.valueOf(_black.color)
            return true
        }
        return super.onTouchEvent(event)
    }
}