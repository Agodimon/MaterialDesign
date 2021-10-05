package com.bignerdranch.android.materialdesign

import android.app.Application
import com.bignerdranch.android.materialdesign.network.ApiService

class App : Application() {

    init {
        instance = this
    }

    val apiService: ApiService by lazy { ApiService.create() }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
