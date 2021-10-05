package com.bignerdranch.android.materialdesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.materialdesign.BuildConfig
import com.bignerdranch.android.materialdesign.model.rest.*
import com.bignerdranch.android.materialdesign.network.ApiService
import com.bignerdranch.android.materialdesign.network.models.MarsPhotoResponse
import com.bignerdranch.android.materialdesign.network.models.MarsPhotosResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel(
    private val apiService: ApiService = ApiService.create()
) : ViewModel() {

    private val observeMarsPhotos: MutableLiveData<PictureOfTheDayDataMars> = MutableLiveData()

    fun getData(): LiveData<PictureOfTheDayDataMars> {
        sendServerRequest()
        return observeMarsPhotos
    }

    private fun sendServerRequest() {
        observeMarsPhotos.value = PictureOfTheDayDataMars.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            apiService.getMarsPhotos(apiKey).enqueue(object : Callback<MarsPhotosResponse> {
                override fun onResponse(
                    call: Call<MarsPhotosResponse>,
                    response: Response<MarsPhotosResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val marsPhotos = response.body()?.photos
                        val marsPhoto = marsPhotos?.firstOrNull()
                        if (marsPhoto == null) {
                            observeMarsPhotos.value =
                                PictureOfTheDayDataMars.Error(Throwable("no photos"))
                        } else {
                            observeMarsPhotos.value =
                                PictureOfTheDayDataMars.Success(marsPhoto)
                        }
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            observeMarsPhotos.value =
                                PictureOfTheDayDataMars.Error(Throwable("Unidentified error"))
                        } else {
                            observeMarsPhotos.value =
                                PictureOfTheDayDataMars.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<MarsPhotosResponse>, t: Throwable) {
                    observeMarsPhotos.value = PictureOfTheDayDataMars.Error(t)
                }
            })
        }
    }
}