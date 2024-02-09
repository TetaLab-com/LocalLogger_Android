package com.tetalab.logcollector.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.tetalab.logcollector.R
import com.tetalab.logcollector.ui.log.LogFragment

/**
 * Todo generalize Activity and Fragment logic
 */
open class JustLogActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)

        handler = Handler(Looper.myLooper()!!)

        initView()
        initListener()
        showLogsFragment()
    }

    private fun initView() {
    }

    private fun initListener() {

    }

    private fun showLogsFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, LogFragment())
            .commit()
    }
}