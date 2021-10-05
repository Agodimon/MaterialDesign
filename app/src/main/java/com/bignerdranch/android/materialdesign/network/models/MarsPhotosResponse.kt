package com.bignerdranch.android.materialdesign.network.models

import com.google.gson.annotations.SerializedName

data class MarsPhotosResponse(
    @field:SerializedName("photos") val photos: List<MarsPhotoResponse>?
)

data class MarsPhotoResponse(
    @field:SerializedName("img_src") val imgSrc: String?,
    @field:SerializedName("earth_date") val earthDate: String?
)