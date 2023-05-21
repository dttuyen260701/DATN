package com.example.realestateapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by tuyen.dang on 5/19/2023.
 */

data class RealEstateDetail(
    @SerializedName("postId") val postId: Int,
    @SerializedName("description") val description: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("createdDate") val createdDate: String,
    @SerializedName("ownerName") val ownerName: String?,
    @SerializedName("phone") val ownerPhone: String?,
    @SerializedName("price") val price: Float,
    @SerializedName("views") val views: Int,
    @SerializedName("isSaved") val isSaved: Boolean,
    @SerializedName("legalName") val legalName: String,
    @SerializedName("propertyTypeName") val propertyTypeName: String,
    @SerializedName("nameDirection") val nameDirection: String,
    @SerializedName("frontal") val width: Float,
    @SerializedName("acreage") val square: Float,
    @SerializedName("parkingSpace") val carParking: Boolean?,
    @SerializedName("streetInFront") val streetInFront: Float?,
    @SerializedName("length") val length: Float,
    @SerializedName("addressDetail") val address: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("bedrooms") val bedrooms: Int?,
    @SerializedName("floors") val floors: Int?,
    @SerializedName("kitchen") val kitchen: Int?,
    @SerializedName("rooftop") val rooftop: Boolean?,
    @SerializedName("diningRoom") val diningRoom: Int?,
    @SerializedName("images") val images: MutableList<Image>
)
