package com.example.realestateapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by tuyen.dang on 5/19/2023.
 */

data class RealEstateDetail(
    @SerializedName("postId") val postId: Int,
    @SerializedName("images") val images: MutableList<String?>,
    @SerializedName("title") val title: String?,
    @SerializedName("createdDate") val createdDate: String,
    @SerializedName("square") val square: Float,
    @SerializedName("price") val price: Float,
    @SerializedName("bedRooms") val bedRooms: Int?,
    @SerializedName("floors") val floors: Int?,
    @SerializedName("kitchen") val kitchen: Boolean?,
    @SerializedName("diningRoom") val diningRoom: Boolean?,
    @SerializedName("carParking") val carParking: Boolean?,
    @SerializedName("rooftop") val rooftop: Boolean?,
    @SerializedName("juridical") val juridical: String,
    @SerializedName("direction") val direction: String,
    @SerializedName("width") val width: Float,
    @SerializedName("length") val length: Float,
    @SerializedName("address") val address: String,
    @SerializedName("description") val description: String?,
    @SerializedName("ownerName") val ownerName: String?,
    @SerializedName("views") val views: Int,
    @SerializedName("isSaved") val isSaved: Boolean,
)
