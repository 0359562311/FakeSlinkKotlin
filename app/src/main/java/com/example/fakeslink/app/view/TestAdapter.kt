package com.example.fakeslink.app.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.fakeslink.R

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url) // image url
        .placeholder(R.drawable.ptit) // any placeholder to load at start
        .error(R.drawable.ptit)  // any image in case of error
        .centerCrop()
        .into(this)
}