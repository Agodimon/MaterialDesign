package com.bignerdranch.android.materialdesign.network.models

import com.google.gson.annotations.SerializedName

data class EarthPhotosResponse(
    @field:SerializedName("url") val url: String?
)