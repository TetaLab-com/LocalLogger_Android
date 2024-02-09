package com.tetalab.logcollector.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tetalab.logcollector.R
import com.tetalab.logcollector.coroutine.AppCoroutineScope
import com.tetalab.logcollector.data.model.Session
import com.tetalab.logcollector.ui.LogsActivity

class HistoryFragment : Fragment() {

    private lateinit var root: View
    private lateinit var historyRecyclerView: RecyclerView

    private lateinit var adapter: HistoryAdapter
    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_history, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scope = AppCoroutineScope()
        viewModel = HistoryViewModel(requireActivity().application, scope)

        initObserver()
        initView()
        initListener()

        viewModel.getSessions()
    }

    private fun initView() {
        historyRecyclerView = root.findViewById(R.id.logsRecyclerView)

        //init list of Logs
        historyRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HistoryAdapter(mutableListOf(), object : HistoryAdapter.IHistoryAdapter {

            override fun onSessionSelected(session: Session) {
                (requireActivity() as LogsActivity).openHistoryLogs(session.uid)
            }
        })
        historyRecyclerView.adapter = adapter
    }

    private fun initListener() {

    }

    private fun initObserver() {
        with(viewModel) {
            sessions.observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.updateData(it)
                }
            }
        }
    }
}