package com.example.fakeslink.app.view

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.fakeslink.R

class MyAlertDialog {
    companion object {
        fun showAlertDialog(context: Context, title: String, message: String) {
            AlertDialog.Builder(context).apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.cancel()
                }
                show()
            }
        }
    }
}