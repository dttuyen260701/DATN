package com.example.realestateapp.data.models

/**
 * Created by tuyen.dang on 6/6/2023.
 */

data class MessageChannel(
    val id: String,
    val messengers: MutableList<ItemMessenger>
)
