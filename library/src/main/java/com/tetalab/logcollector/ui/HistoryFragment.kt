package com.tetalab.logcollector.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tetalab.logcollector.R
import com.tetalab.logcollector.data.source.SessionDataSource
import com.tetalab.logcollector.ui.adapter.HistoryAdapter

class HistoryFragment : Fragment() {

    private lateinit var root: View
    private lateinit var historyRecyclerView: RecyclerView

    private lateinit var adapter: HistoryAdapter

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
        initView()
        initListener()
    }

    private fun initView() {
        historyRecyclerView = root.findViewById(R.id.logsRecyclerView)

        //init list of Logs
        historyRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HistoryAdapter(SessionDataSource.getSessions().toMutableList())
        historyRecyclerView.adapter = adapter
    }

    private fun initListener() {
    }
}