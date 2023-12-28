package com.tetalab.logcollector

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tetalab.logcollector.data.model.Level
import com.tetalab.logcollector.data.source.LogsDataSource
import com.tetalab.logcollector.ui.LogsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Todo generalize Activity and Fragment logic
 */
open class LogActivity : AppCompatActivity() {

    private lateinit var logsRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var levelView: Spinner
    private lateinit var shareView: View

    private lateinit var adapter: LogsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)

        initView()
        initListener()
    }

    private fun initView() {
        Log.d("LogActivity", "initView")
        logsRecyclerView = findViewById(R.id.logsRecyclerView)
        searchView = findViewById(R.id.searchFilter)
        levelView = findViewById(R.id.filterLevel)
        shareView = findViewById(R.id.shareAsFile)

        //init list of Logs
        logsRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = LogsAdapter(mutableListOf())
        logsRecyclerView.adapter = adapter

        //init list of level
        val items = arrayOf("ALL",
            Level.WARNING.value,
            Level.INFO.value,
            Level.ERROR.value,
            Level.IN_MESSAGE.value,
            Level.OUT_MESSAGE.value)
        val levelAdapter = ArrayAdapter(this, R.layout.spinner_item, items)

        levelView.adapter = levelAdapter

        //todo set initial data for search view and level filter
    }

    private fun initListener() {
        Log.d("LogActivity", "initListener")
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
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

        levelView.onItemSelectedListener = object : OnItemSelectedListener {
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
        startActivity(shareIntent)
    }

    // Coroutine listening for Locations
    private var locationUpdatesJob: Job? = null

    override fun onStart() {
        super.onStart()
        Log.d("LogActivity", "onStart")
        locationUpdatesJob = lifecycleScope.launch {
            LogsDataSource.logsFlow.collect {
                withContext(Dispatchers.Main) {
                    adapter.updateData(it)
                }
            }
        }
    }

    override fun onStop() {
        Log.d("LogActivity", "onStop")
        // Stop collecting when the View goes to the background
        locationUpdatesJob?.cancel()
        super.onStop()
    }
}