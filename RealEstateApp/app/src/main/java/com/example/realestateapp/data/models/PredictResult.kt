package com.example.realestateapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by tuyen.dang on 6/3/2023.
 */

data class PredictResult(
    @SerializedName("cluster") val cluster: Int,
    @SerializedName("result") val result: Float,
)
