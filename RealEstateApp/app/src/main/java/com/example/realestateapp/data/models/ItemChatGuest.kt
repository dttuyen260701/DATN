package com.example.realestateapp.data.models

/**
 * Created by tuyen.dang on 6/3/2023.
 */

data class ItemChatGuest(
    val idChannel: String = "",
    val idGuest: String = "",
    val nameGuest: String = "",
    val imageGuest: String = "",
    val lastMessage: String = "",
    val idUserSend: String = "",
    val read: Boolean = false,
)
