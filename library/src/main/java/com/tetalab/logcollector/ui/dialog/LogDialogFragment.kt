package com.tetalab.logcollector.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.tetalab.logcollector.R
import com.tetalab.logcollector.data.source.LogsDataSource
import com.tetalab.logcollector.ui.LogsViewController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogDialogFragment : DialogFragment() {

    private lateinit var root: View

    private lateinit var logsViewController: LogsViewController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_just_logs, container, false)
        logsViewController = LogsViewController(root, requireContext())
        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logsViewController.initView()
        logsViewController.initListener()
    }

    // Coroutine listening for Locations
    private var locationUpdatesJob: Job? = null

    override fun onStart() {
        super.onStart()
        Log.d("JustLogActivity", "onStart")
        locationUpdatesJob = lifecycleScope.launch {
            LogsDataSource.logsFlow.collect {
                withContext(Dispatchers.Main) {
                    logsViewController.updateData(it)
                }
            }
        }

        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

    override fun onStop() {
        Log.d("JustLogActivity", "onStop")
        // Stop collecting when the View goes to the background
        locationUpdatesJob?.cancel()
        super.onStop()
    }

    companion object {
        val TAG: String = LogDialogFragment::class.java.name
    }
}