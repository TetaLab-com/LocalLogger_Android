package com.tetalab.logcollector.ui.log

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.tetalab.logcollector.R
import com.tetalab.logcollector.data.source.LogsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogFragment : Fragment() {

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
    }

    override fun onStop() {
        Log.d("JustLogActivity", "onStop")
        // Stop collecting when the View goes to the background
        locationUpdatesJob?.cancel()
        super.onStop()
    }

    companion object {
        val TAG: String = LogFragment::class.java.name
    }
}