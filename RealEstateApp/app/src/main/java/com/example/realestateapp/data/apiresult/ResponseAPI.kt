package com.example.realestateapp.data.apiresult

import com.google.gson.annotations.SerializedName

data class ResponseAPI<T>(
    @SerializedName("isSuccesses") var isSuccesses: Boolean,
    @SerializedName("message") var message: String?,
    @SerializedName("resultObj") var resultObj: T
)
