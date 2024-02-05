package com.tetalab.logcollector

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.tetalab.logcollector.ui.AboutFragment
import com.tetalab.logcollector.ui.HistoryFragment
import com.tetalab.logcollector.ui.LogFragment


class LogsActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    private lateinit var bottomNavView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)

        handler = Handler(Looper.myLooper()!!)

        initView()
        initListener()
        showLogsFragment()
    }

    private fun initView() {
        bottomNavView = findViewById(R.id.bottom_navigation)
    }

    private fun initListener() {
        bottomNavView.setOnItemSelectedListener(navListener)
    }

    private fun showLogsFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, LogFragment())
            .commit()
    }

    private val navListener =
        NavigationBarView.OnItemSelectedListener { item: MenuItem ->
            // By using switch we can easily get
            // the selected fragment
            // by using there id.
            var selectedFragment: Fragment? = null
            val itemId = item.itemId
            if (itemId == R.id.session) {
                selectedFragment = LogFragment()
            } else if (itemId == R.id.history) {
                selectedFragment = HistoryFragment()
            } else if (itemId == R.id.about) {
                selectedFragment = AboutFragment()
            }
            // It will help to replace the
            // one fragment to other.
            if (selectedFragment != null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, selectedFragment)
                    .commit()
            }
            true
        }
}