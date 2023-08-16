package com.example.realestateapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by tuyen.dang on 5/7/2023.
 */

data class User(
    @SerializedName("id") val id: Int = 1,
    @SerializedName("phoneNumber") val phoneNumber: String = "1234567890",
    @SerializedName("fullName") val fullName: String = "Testing",
    @SerializedName("email") val email: String = "test@gmail.com",
    @SerializedName("imageUser") val imgUrl: String? = "https://binhminhdigital.com/StoreData/PageData/2372/nhung-loi-co-ban-khi-chup-anh-phong-canh%20(5).jpg",
    @SerializedName("dateOfBirth") val dateOfBirth: String = "26/07/2001",
    @SerializedName("gender") val gender: String = "1",
    @SerializedName("cccd") val cccd: String? = "123123123",
    @SerializedName("imageCCCD") val imageCCCD: String? = "imgCCCD",
    @SerializedName("addressDetail") val addressDetail: String? = "SO 2",
    @SerializedName("provinceId") val provinceId: String? = "1",
    @SerializedName("provinceName") val provinceName: String? = "Da Nang",
    @SerializedName("districtId") val districtId: String? = "1",
    @SerializedName("districtName") val districtName: String? = "Hai Chau",
    @SerializedName("wardId") val wardId: String? = "1",
    @SerializedName("wardName") val wardName: String? = "Hoa Cuong Nam",
    @SerializedName("zalo") val zalo: String? = "1234567890",
    @SerializedName("facebook") val facebook: String? = "fb",
    @SerializedName("status") val status: Int = 1,
    @SerializedName("levelRank") val levelRank: Int = 1,
    @SerializedName("accountBalance") val accountBalance: Int = 1,
    @SerializedName("isIdentified") val isIdentified: Boolean = true,
    @SerializedName("token") val token: String = ""
)
