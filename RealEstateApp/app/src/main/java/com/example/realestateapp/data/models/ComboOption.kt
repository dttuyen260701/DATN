package com.example.realestateapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by tuyen.dang on 6/5/2023.
 */

data class ComboOption(
    @SerializedName("id") val id: Int,
    @SerializedName("comboOptionTypeId") val comboOptionTypeId: Int,
    @SerializedName("comboOptionName") val comboOptionName: String,
    @SerializedName("description") val description: String,
    @SerializedName("dayNumber") val dayNumber: Int,
    @SerializedName("amount") val amount: Float,
)
