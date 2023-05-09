package com.example.realestateapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by tuyen.dang on 5/7/2023.
 */

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("imageUser") val imgUrl: String?,
    @SerializedName("dateOfBirth") val dateOfBirth: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("cccd") val cccd: String?,
    @SerializedName("imageCCCD") val imageCCCD: String?,
    @SerializedName("addressDetail") val addressDetail: String?,
    @SerializedName("provinceId") val provinceId: String?,
    @SerializedName("provinceName") val provinceName: String?,
    @SerializedName("districtId") val districtId: String?,
    @SerializedName("districtName") val districtName: String?,
    @SerializedName("wardId") val wardId: String?,
    @SerializedName("wardName") val wardName: String?,
    @SerializedName("zalo") val zalo: String?,
    @SerializedName("facebook") val facebook: String?,
    @SerializedName("status") val status: Int,
    @SerializedName("levelRank") val levelRank: Int,
    @SerializedName("accountBalance") val accountBalance: Int,
    @SerializedName("isIdentified") val isIdentified: Boolean,
    @SerializedName("token") val token: Boolean
)
