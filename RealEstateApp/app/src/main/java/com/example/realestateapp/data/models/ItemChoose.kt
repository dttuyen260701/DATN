package com.example.realestateapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by tuyen.dang on 5/12/2023.
 */

data class ItemChoose(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("score") val score: Int = -1,
    var isSelected: Boolean = false
)
