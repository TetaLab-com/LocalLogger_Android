package com.tetalab.logcollector.ui.log

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.tetalab.logcollector.R
import com.tetalab.logcollector.coroutine.AppCoroutineScope

class LogFragment : Fragment() {

    private lateinit var root: View

    private lateinit var logsViewController: LogsViewController
    private lateinit var backBtn: View
    private lateinit var title: TextView
    private var sessionId: Int = -1

    private lateinit var viewModel: LogViewModel

    companion object {
        val TAG: String = LogFragment::class.java.name

        const val KEY_SESSION_ID = "SESSION_ID"

        fun newInstance(sessionId: Int): LogFragment {
            val f = LogFragment()
            val args = Bundle()
            args.putInt(KEY_SESSION_ID, sessionId)
            f.arguments = args
            return f
        }
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

        initView()
        initListener()

        if (arguments == null) {
            //read into about current Session
        } else {
            sessionId = requireArguments().getInt(KEY_SESSION_ID)
            viewModel.setSession(sessionId)
        }
    }

    private fun initView() {
        backBtn = root.findViewById(R.id.backBtn)
        title = root.findViewById(R.id.title)
    }

    private fun initListener() {
        backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initObserver() {
        with(viewModel) {
            logs.observe(viewLifecycleOwner) {
                if (it != null) {
                    logsViewController.updateData(it)
                }
            }
            session.observe(viewLifecycleOwner) {
                if (it != null) {
                    backBtn.visibility = View.VISIBLE
                    title.text = it.dateTime
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