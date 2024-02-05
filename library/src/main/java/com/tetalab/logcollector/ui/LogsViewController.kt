package com.tetalab.logcollector.ui

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tetalab.logcollector.R
import com.tetalab.logcollector.data.model.Level
import com.tetalab.logcollector.data.model.AppLog
import com.tetalab.logcollector.data.source.LogsDataSource
import com.tetalab.logcollector.ui.adapter.LogsAdapter

class LogsViewController(private val root: View, private val context: Context) {

    private lateinit var logsRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var levelView: Spinner
    private lateinit var shareView: View

    private lateinit var adapter: LogsAdapter


    fun initView() {
        android.util.Log.d("JustLogActivity", "initView")
        logsRecyclerView = root.findViewById(R.id.logsRecyclerView)
        searchView = root.findViewById(R.id.searchFilter)
        levelView = root.findViewById(R.id.filterLevel)
        shareView = root.findViewById(R.id.shareAsFile)

        //init list of Logs
        logsRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = LogsAdapter(mutableListOf())
        logsRecyclerView.adapter = adapter

        //init list of level
        val items = arrayOf("ALL",
            Level.WARNING.value,
            Level.INFO.value,
            Level.ERROR.value,
            Level.IN_MESSAGE.value,
            Level.OUT_MESSAGE.value)
        val levelAdapter = ArrayAdapter(context, R.layout.spinner_item, items)

        levelView.adapter = levelAdapter

        //todo set initial data for search view and level filter
    }

    fun initListener() {
        android.util.Log.d("JustLogActivity", "initListener")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    LogsDataSource.removeSearchQuery()
                } else {
                    LogsDataSource.updateSearchQuery(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    LogsDataSource.removeSearchQuery()
                } else {
                    LogsDataSource.updateSearchQuery(newText)
                }
                return true
            }
        })

        levelView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
//                "ALL",
                when(position) {
                    0 -> LogsDataSource.filterLevelAll()
                    1 -> LogsDataSource.filterLevel(Level.WARNING)
                    2 -> LogsDataSource.filterLevel(Level.INFO)
                    3 -> LogsDataSource.filterLevel(Level.ERROR)
                    4 -> LogsDataSource.filterLevel(Level.IN_MESSAGE)
                    5 -> LogsDataSource.filterLevel(Level.OUT_MESSAGE)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        shareView.setOnClickListener {
            startShareIntent()
        }
    }

    private fun startShareIntent() {
        val logs = LogsDataSource.getFilteredLogs()
        var text: StringBuilder = StringBuilder()
        for (log in logs) {
            text.append(log.toString())
            text.append("\n")
        }
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text.toString())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

    fun updateData(it: List<AppLog>) {
        adapter.updateData(it)
    }
}