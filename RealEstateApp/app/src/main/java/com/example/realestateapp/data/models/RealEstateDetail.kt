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
    @SerializedName("suggestedPrice") val suggestedPrice: Double,
    @SerializedName("price") val price: Double,
    @SerializedName("views") val views: Int,
    @SerializedName("isSaved") val isSaved: Boolean,
    @SerializedName("legalId") val legalId: Int,
    @SerializedName("legalName") val legalName: String,
    @SerializedName("propertyTypeId") val propertyTypeId: Int,
    @SerializedName("propertyTypeName") val propertyTypeName: String,
    @SerializedName("districtId") val districtId: Int,
    @SerializedName("districtName") val districtName: String,
    @SerializedName("wardId") val wardId: Int,
    @SerializedName("wardName") val wardName: String,
    @SerializedName("streetId") val streetId: Int,
    @SerializedName("streetName") val streetName: String,
    @SerializedName("directionId") val directionId: Int,
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
    @SerializedName("images") val images: MutableList<Image>,
    @SerializedName("status") val status: Int,
    @SerializedName("ownerId") val ownerId: Int,
    @SerializedName("imageOwner") val imageOwner: String,
    @SerializedName("dueDate") val dueDate: String,
    @SerializedName("comboOptionId") val comboOptionId: Int,
    @SerializedName("comboOptionName") val comboOptionName: String
)
