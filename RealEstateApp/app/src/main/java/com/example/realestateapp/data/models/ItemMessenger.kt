package com.example.realestateapp.data.models

/**
 * Created by tuyen.dang on 6/4/2023.
 */

data class ItemMessenger(
    val timeMilliseconds: Long = 0,
    val idUserSend: Int = -1,
    val messenger: String = "",
    val typeMessage: Int = 0,
    val isSending: Boolean = false
)
