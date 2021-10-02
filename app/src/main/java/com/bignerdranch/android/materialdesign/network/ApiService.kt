package com.bignerdranch.android.materialdesign.network

import com.bignerdranch.android.materialdesign.network.models.EarthPhotosResponse
import com.bignerdranch.android.materialdesign.network.models.MarsPhotosResponse
import com.bignerdranch.android.materialdesign.network.models.PODServerResponseData
import com.bignerdranch.android.materialdesign.network.utils.addJsonConverter
import com.bignerdranch.android.materialdesign.network.utils.setClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/"

interface ApiService {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODServerResponseData>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsPhotos(
        @Query("api_key") apiKey: String,
        @Query("sol") sol: Int = 1000,
        @Query("camera") camera: String = "NAVCAM"
    ): Call<MarsPhotosResponse>


    @GET("planetary/earth/imagery")
    fun getEarthPhotos(
        @Query("api_key") apiKey: String,
        @Query("lon") lon: Double = 100.75,
        @Query("lat") lat: Double = 1.5,
        @Query("date") date: String = "2014-02-01"
    ): Call<EarthPhotosResponse>


    companion object {
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .setClient()
                .addJsonConverter()
                .build()
                .create(ApiService::class.java)
        }
    }
}
