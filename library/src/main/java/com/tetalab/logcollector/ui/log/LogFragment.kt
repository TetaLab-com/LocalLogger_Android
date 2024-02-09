package com.tetalab.logcollector.ui.log

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.tetalab.logcollector.R
import com.tetalab.logcollector.coroutine.AppCoroutineScope
import com.tetalab.logcollector.data.source.LogsDataSource
import com.tetalab.logcollector.ui.history.HistoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogFragment : Fragment() {

    private lateinit var root: View

    private lateinit var logsViewController: LogsViewController
    private var sessionId: Int = -1

    private lateinit var viewModel: LogViewModel

    companion object {
        val TAG: String = LogFragment::class.java.name

        const val KEY_SESSION_ID = "SESSION_ID"
    }

    fun newInstance(sessionId: Int): LogFragment {
        val f = LogFragment()
        val args = Bundle()
        args.putInt(KEY_SESSION_ID, sessionId)
        f.arguments = args
        return f
    }

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
        val scope = AppCoroutineScope()
        viewModel = LogViewModel(requireActivity().application, scope)
        initObserver()
        logsViewController.initView()
        logsViewController.initListener()

        if(arguments == null) {
            //read into about current Session
        } else {
            sessionId = requireArguments().getInt(KEY_SESSION_ID)

        }
    }

    private fun initObserver() {
        with(viewModel) {
            logs.observe(viewLifecycleOwner) {
                if (it != null) {
                    logsViewController.updateData(it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("JustLogActivity", "onStart")
        viewModel.onStart()
    }

    override fun onStop() {
        Log.d("JustLogActivity", "onStop")
        viewModel.onStop()
        super.onStop()
    }
}