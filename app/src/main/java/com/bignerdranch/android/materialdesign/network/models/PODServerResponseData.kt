package com.bignerdranch.android.materialdesign.network.models

import com.google.gson.annotations.SerializedName

data class PODServerResponseData(
    @field:SerializedName("explanation") val explanation: String?,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("url") val image: String?
)
