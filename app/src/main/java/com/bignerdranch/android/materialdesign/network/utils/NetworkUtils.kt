package com.bignerdranch.android.materialdesign.network.utils

import com.bignerdranch.android.materialdesign.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit.Builder.setClient() = apply {
    val okHttpClient = OkHttpClient.Builder()
        .addHttpLoggingInterceptor()
        .build()

    this.client(okHttpClient)
}

private fun OkHttpClient.Builder.addHttpLoggingInterceptor() = apply {
    if (BuildConfig.DEBUG) {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        this.addNetworkInterceptor(interceptor)
    }
}

fun Retrofit.Builder.addJsonConverter() = apply {
    this.addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
}
