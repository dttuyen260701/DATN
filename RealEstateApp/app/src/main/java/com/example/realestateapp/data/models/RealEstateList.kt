package com.example.realestateapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by tuyen.dang on 5/12/2023.
 */

data class RealEstateList(
    @SerializedName("id") val id: Int,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("createdDate") val createdDate: String,
    @SerializedName("square") val square: Float,
    @SerializedName("price") val price: Double,
    @SerializedName("bedRooms") val bedRooms: Int?,
    @SerializedName("floors") val floors: Int?,
    @SerializedName("address") val address: String,
    @SerializedName("views") val views: Int,
    @SerializedName("isSaved") val isSaved: Boolean,
    @SerializedName("status") val status: Int,
)
