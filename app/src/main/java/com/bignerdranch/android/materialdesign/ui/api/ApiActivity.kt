package com.bignerdranch.android.materialdesign.ui.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.materialdesign.R
import kotlinx.android.synthetic.main.activity_api.*

class ApiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        view_pager.adapter = ViewPagerAdapter(baseContext, supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }
}