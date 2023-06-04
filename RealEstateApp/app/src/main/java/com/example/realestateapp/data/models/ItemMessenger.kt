package com.example.realestateapp.data.models

/**
 * Created by tuyen.dang on 6/4/2023.
 */

data class ItemMessenger(
    val timeMilliseconds: Long,
    val idUserSend: Int,
    val messenger: String,
    val isPhoto: Boolean = false,
    val isSending: Boolean = false
)
