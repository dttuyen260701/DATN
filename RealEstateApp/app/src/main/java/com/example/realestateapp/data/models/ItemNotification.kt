package com.example.realestateapp.data.models

/**
 * Created by tuyen.dang on 6/8/2023.
 */

data class ItemNotification(
    val idPost: Int = -1,
    val image: String = "",
    val messenger: String = "",
    val read: Boolean = false
)
