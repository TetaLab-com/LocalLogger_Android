package com.tetalab.logcollector

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.tetalab.logcollector.ui.LogFragment

class LogsActivity : AppCompatActivity() {

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