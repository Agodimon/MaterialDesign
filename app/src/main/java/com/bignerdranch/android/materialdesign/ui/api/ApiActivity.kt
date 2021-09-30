package com.bignerdranch.android.materialdesign.ui.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.bignerdranch.android.materialdesign.R
import kotlinx.android.synthetic.main.activity_api.*

class ApiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        view_pager.adapter = ViewPagerAdapter(baseContext, supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
        setCustomTabs()
    }

    private fun setCustomTabs() {
        tab_layout.getTabAt(0)?.setIcon(R.drawable.ic_earth)
        tab_layout.getTabAt(1)?.setIcon(R.drawable.ic_mars)
        tab_layout.getTabAt(2)?.setIcon(R.drawable.ic_weather)
    }
}