package com.example.fakeslink

import com.example.fakeslink.app.model.Session
import com.example.fakeslink.app.model.Student


object GlobalVariable {
    var session: Session? = null
    var currentUser: Student? = null

    fun reset() {
        session = null
        currentUser = null
    }
}