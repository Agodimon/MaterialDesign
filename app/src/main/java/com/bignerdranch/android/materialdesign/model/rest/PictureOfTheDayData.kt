package com.bignerdranch.android.materialdesign.model.rest

import com.bignerdranch.android.materialdesign.network.models.EarthPhotosResponse
import com.bignerdranch.android.materialdesign.network.models.MarsPhotoResponse
import com.bignerdranch.android.materialdesign.network.models.PODServerResponseData

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}
sealed class PictureOfTheDayDataMars {
    data class Success(val serverResponseData: MarsPhotoResponse) : PictureOfTheDayDataMars()
    data class Error(val error: Throwable) : PictureOfTheDayDataMars()
    data class Loading(val progress: Int?) : PictureOfTheDayDataMars()
}

sealed class PictureOfTheDayDataEarth {
    data class Success(val serverResponseData: EarthPhotosResponse) : PictureOfTheDayDataEarth()
    data class Error(val error: Throwable) : PictureOfTheDayDataEarth()
    data class Loading(val progress: Int?) : PictureOfTheDayDataEarth()
}
