package com.bignerdranch.android.materialdesign.ui

import SETTINGS_SHARED_PREFERENCES
import THEME_RES_ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.materialdesign.R
import com.bignerdranch.android.materialdesign.ui.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(
            getSharedPreferences(SETTINGS_SHARED_PREFERENCES, MODE_PRIVATE)
                .getInt(THEME_RES_ID, R.style.DefaultTheme)
        )
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNowAllowingStateLoss()
        }
    }
}