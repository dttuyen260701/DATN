package com.example.realestateapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by tuyen.dang on 5/20/2023.
 */

data class Image(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
)
