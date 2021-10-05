package com.bignerdranch.android.materialdesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.materialdesign.BuildConfig
import com.bignerdranch.android.materialdesign.model.rest.*
import com.bignerdranch.android.materialdesign.network.ApiService
import com.bignerdranch.android.materialdesign.network.models.EarthPhotosResponse
import com.bignerdranch.android.materialdesign.network.models.MarsPhotoResponse
import com.bignerdranch.android.materialdesign.network.models.MarsPhotosResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel(
    private val apiService: ApiService = ApiService.create()
) : ViewModel() {

    private val observeEarthPhotos: MutableLiveData<PictureOfTheDayDataEarth> = MutableLiveData()

    fun getData(): LiveData<PictureOfTheDayDataEarth> {
        sendServerRequest()
        return observeEarthPhotos
    }

    private fun sendServerRequest() {
        observeEarthPhotos.value = PictureOfTheDayDataEarth.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            PictureOfTheDayDataEarth.Error(Throwable("You need API key"))
        } else {
            apiService.getEarthPhotos(apiKey).enqueue(object : Callback<EarthPhotosResponse> {
                override fun onResponse(
                    call: Call<EarthPhotosResponse>,
                    response: Response<EarthPhotosResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val earthPhoto = response.body()?.url
                        if (earthPhoto == null) {
                            observeEarthPhotos.value =
                                PictureOfTheDayDataEarth.Error(Throwable("no photos"))
                        }
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            observeEarthPhotos.value =
                                PictureOfTheDayDataEarth.Error(Throwable("Unidentified error"))
                        } else {
                            observeEarthPhotos.value =
                                PictureOfTheDayDataEarth.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<EarthPhotosResponse>, t: Throwable) {
                    observeEarthPhotos.value = PictureOfTheDayDataEarth.Error(t)
                }
            })
        }
    }
}