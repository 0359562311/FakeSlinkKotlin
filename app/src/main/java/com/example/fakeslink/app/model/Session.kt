package com.example.fakeslink.app.model

import com.google.gson.annotations.SerializedName

data class Session (
    @SerializedName("refresh")
    val refresh: String,
    @SerializedName("access")
    var access: String,
)