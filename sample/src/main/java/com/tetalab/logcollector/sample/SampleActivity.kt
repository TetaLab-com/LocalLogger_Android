package com.tetalab.logcollector.sample

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tetalab.logcollector.AppLogLibrary
import com.tetalab.logcollector.coroutine.AppCoroutineScope
import com.tetalab.logcollector.ui.LogsActivity
import com.tetalab.logcollector.data.source.LogsDataSource
import com.tetalab.logcollector.ui.dialog.LogDialogFragment
import com.tetalab.logcollector.ui.history.HistoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SampleActivity : AppCompatActivity() {

    private lateinit var showLogsDialogBtn: Button
    private lateinit var showLogsActivityBtn: Button
    private lateinit var handler: Handler

    private var messageNumber = 1
    private lateinit var viewModel: SampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        AppLogLibrary.init(this)

        val scope = AppCoroutineScope()
        viewModel = SampleViewModel(application, scope)

        handler = Handler(Looper.myLooper()!!)
        initView()
        initListener()
    }

    private fun initView() {
        showLogsDialogBtn = findViewById(R.id.showLogsDialogBtn)
        showLogsActivityBtn = findViewById(R.id.showLogsActivityBtn)
    }

    private fun initListener() {
        showLogsDialogBtn.setOnClickListener {
            showLogsDialog()
        }

        showLogsActivityBtn.setOnClickListener {
            showLogsActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        sendTestMessages()
    }

    private fun showLogsActivity() {
        startActivity(Intent(this, LogsActivity::class.java))
    }

    private fun showLogsDialog() {
        LogDialogFragment().show(
            supportFragmentManager, LogDialogFragment.TAG)
    }

    private fun sendTestMessages() {
        handler.postDelayed({
            viewModel.wLog("testClass", "testMessage", "Message")
        }, 1000)
        handler.postDelayed({
            viewModel.iLog("testClass2", "testMessage2", "Message2")
        }, 2000)
        handler.postDelayed({
            viewModel.eLog("testClass3", "testMessage3", "Message3")
        }, 3000)
        handler.postDelayed({
            viewModel.inMessageLog("testClass4", "testMessage4", "Message4")
        }, 4000)
        handler.postDelayed({
            viewModel.outMessageLog("testClass5", "testMessage5", "Message5")
        }, 5000)

        scheduleDataUpdates()
    }

    override fun onPause() {
        stopDataUpdates()
        super.onPause()
    }

    private val sendMessagesRunnable = Runnable {
        viewModel.wLog("Message$messageNumber")
        viewModel.iLog("Message$messageNumber")
        viewModel.eLog("Message$messageNumber")
        viewModel.inMessageLog("Message$messageNumber")
        viewModel.outMessageLog("Message$messageNumber")
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