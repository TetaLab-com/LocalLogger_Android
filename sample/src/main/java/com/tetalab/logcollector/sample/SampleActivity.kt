package com.tetalab.logcollector.sample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tetalab.logcollector.data.source.LogsDataSource
import com.tetalab.logcollector.ui.LogFragment

class SampleActivity : AppCompatActivity() {

    private lateinit var showLogsBtn: Button
    private lateinit var handler: Handler

    private var messageNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        handler = Handler(Looper.myLooper()!!)

        initView()
        initListener()
    }

    private fun initView() {
        showLogsBtn = findViewById(R.id.showLogsBtn)
    }

    private fun initListener() {
        showLogsBtn.setOnClickListener {
            showLogsFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        sendTestMessages()
    }

    private fun showLogsFragment() {
        LogFragment().show(
            supportFragmentManager, LogFragment.TAG)
    }

    private fun sendTestMessages() {
        handler.postDelayed({
            LogsDataSource.w("testClass", "testMessage", "Message")
        }, 1000)
        handler.postDelayed({
            LogsDataSource.i("testClass2", "testMessage2", "Message2")
        }, 2000)
        handler.postDelayed({
            LogsDataSource.e("testClass3", "testMessage3", "Message3")
        }, 3000)
        handler.postDelayed({
            LogsDataSource.inMessage("testClass4", "testMessage4", "Message4")
        }, 4000)
        handler.postDelayed({
            LogsDataSource.outMessage("testClass5", "testMessage5", "Message5")
        }, 5000)

        scheduleDataUpdates()
    }

    override fun onPause() {
        stopDataUpdates()
        super.onPause()
    }

    private val sendMessagesRunnable = Runnable {
        LogsDataSource.w("Message$messageNumber")
        LogsDataSource.i("Message$messageNumber")
        LogsDataSource.e("Message$messageNumber")
        LogsDataSource.inMessage("Message$messageNumber")
        LogsDataSource.outMessage("Message$messageNumber")
        messageNumber++
        scheduleDataUpdates()
    }

    private fun scheduleDataUpdates() {
        handler.postDelayed(sendMessagesRunnable, 1000)
    }

    private fun stopDataUpdates() {
        handler.removeCallbacks(sendMessagesRunnable)
    }
}