package com.example.realestateapp.data.apiresult

import com.google.gson.annotations.SerializedName

data class ResponseAPI<T>(
    @SerializedName("isSuccess") var isSuccess: Boolean,
    @SerializedName("errorMessage") var errorMessage: String?,
    @SerializedName("body") var body: T
)
