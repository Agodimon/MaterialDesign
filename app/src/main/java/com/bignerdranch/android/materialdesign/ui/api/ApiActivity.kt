package com.bignerdranch.android.materialdesign.ui.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.materialdesign.R
import com.bignerdranch.android.materialdesign.databinding.ActivityApiBinding

class ApiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initContent()
    }

    private fun initContent() {
        binding.viewPager.adapter = ViewPagerAdapter(baseContext, supportFragmentManager)

        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_earth)
        binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_mars)
        binding.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_weather)
    }
}