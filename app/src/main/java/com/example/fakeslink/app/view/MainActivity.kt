package com.example.fakeslink.app.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.fakeslink.GlobalVariable
import com.example.fakeslink.R
import com.example.fakeslink.app.model.Session
import com.example.fakeslink.utils.MyBroadcastReceiver
import com.example.fakeslink.utils.MyLocalBroadcastReceiver
import com.example.fakeslink.utils.SharePreferenceUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.Serializable

class MainActivity : AppCompatActivity(), Serializable {

    private lateinit var myBroadcastReceiver: MyBroadcastReceiver
    private lateinit var myLocalBroadcastReceiver: MyLocalBroadcastReceiver
    private var currentBottomBarId = R.id.bottom_nav_home
    private lateinit var fragments: MutableList<Fragment>
    private val bottomId = listOf(
        R.id.bottom_nav_home,
        R.id.bottom_nav_notification,
        R.id.bottom_nav_study_corner,
        R.id.bottom_nav_facility,
        R.id.bottom_nav_personal_info,
    )
    private val fragmentTags = listOf("MainHome", "MainNotification", "MainStudyCorner", "MainEvent", "MainPersonalInfo")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState != null) {
            currentBottomBarId = savedInstanceState.getInt("currentBottomBarId", currentBottomBarId)
            fragments = mutableListOf()
            for (i in 0 until 5) {
                fragments.add(supportFragmentManager.findFragmentByTag(fragmentTags[i])!!)
            }
        } else {
            fragments = mutableListOf(
                SettingFragment.newInstance(this),
                ListNotificationsFragment(),
                ListNotificationsFragment(),
                ListNotificationsFragment(),
                ListNotificationsFragment(),
            )
            for (i in 0 until fragments.size) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.main_fragment, fragments[i], fragmentTags[i])
                    .hide(fragments[i])
                    .commit()
            }
        }
        /// Register broadcast
        myBroadcastReceiver = MyBroadcastReceiver()
        myLocalBroadcastReceiver = MyLocalBroadcastReceiver()
        val intentFilterBroadcast = IntentFilter()
        intentFilterBroadcast.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(myBroadcastReceiver, intentFilterBroadcast)
        val intentFilterLocalBroadcast = IntentFilter(MyLocalBroadcastReceiver.SESSION_EXPIRED)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(myLocalBroadcastReceiver, intentFilterLocalBroadcast)

        /// Get user's session
        SharePreferenceUtils.initialize(this)
        if (SharePreferenceUtils.getString("access") != null) GlobalVariable.session = Session(
            refresh = SharePreferenceUtils.getString("refresh")!!,
            access = SharePreferenceUtils.getString("access")!!
        )
        Log.d("TanKiem", GlobalVariable.session?.access ?: "no access")
        if (GlobalVariable.session == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            this.finish()
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        supportFragmentManager.beginTransaction()
            .show(fragments[0])
            .commit()
        bottomNavigationView.setOnItemSelectedListener { listener ->
            if (listener.itemId == currentBottomBarId) {
                return@setOnItemSelectedListener false
            }
            supportFragmentManager.beginTransaction()
                .show(fragments[0])
            supportFragmentManager.beginTransaction()
                .show(fragments[bottomId.indexOf(listener.itemId)])
                .hide(fragments[bottomId.indexOf(currentBottomBarId)])
                .commit()
            currentBottomBarId = listener.itemId
            true
        }
    }

    fun logout() {
        SharePreferenceUtils.removeKey("access")
        SharePreferenceUtils.removeKey("refresh")
        GlobalVariable.reset()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt("currentBottomBarId", currentBottomBarId)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myLocalBroadcastReceiver)
        unregisterReceiver(myBroadcastReceiver)
        super.onDestroy()
    }
}