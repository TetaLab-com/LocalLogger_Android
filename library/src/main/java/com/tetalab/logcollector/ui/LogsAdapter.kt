package com.tetalab.logcollector.ui

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.tetalab.logcollector.R
import com.tetalab.logcollector.data.model.Level
import com.tetalab.logcollector.data.model.Log

class LogsAdapter(private val localDataSet: MutableList<Log>) : RecyclerView.Adapter<LogsAdapter.ViewHolder>() {

    class ViewHolder(var root: View) : RecyclerView.ViewHolder(root) {
        val dateView: TextView = root.findViewById<View>(R.id.dateView) as TextView
        val textView: TextView = root.findViewById<View>(R.id.textView) as TextView

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)
        android.util.Log.d("LogsAdapter", "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var log = localDataSet[position]
        viewHolder.dateView.text = log.dateTime
        viewHolder.textView.text = "${log.level.getLevelPrefix()}${log.getUserMessage()}"
        viewHolder.textView.setTextColor(log.level.color)

        if (position % 2 == 0) {
            viewHolder.root.setBackgroundColor(viewHolder.root.resources.getColor(R.color.row_pair))
        } else {
            viewHolder.root.setBackgroundColor(viewHolder.root.resources.getColor(R.color.row_odd))
        }
    }

    override fun getItemCount(): Int {
        android.util.Log.d("LogsAdapter", "localDataSet.size: " + localDataSet.size)

        return localDataSet.size
    }

    fun updateData(newData: List<Log>) {
        android.util.Log.d("LogsAdapter", "updateData newData.size: " + newData.size)

        localDataSet.clear()
        localDataSet.addAll(newData)
        notifyDataSetChanged()
    }
}
