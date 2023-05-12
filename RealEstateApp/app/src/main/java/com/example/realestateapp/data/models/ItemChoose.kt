package com.example.realestateapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by tuyen.dang on 5/12/2023.
 */

data class ItemChoose(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    var isSelected: Boolean = false
)
