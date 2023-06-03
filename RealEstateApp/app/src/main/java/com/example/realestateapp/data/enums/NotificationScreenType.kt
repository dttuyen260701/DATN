package com.example.realestateapp.data.enums

import com.example.realestateapp.data.models.ItemChoose

/**
 * Created by tuyen.dang on 6/3/2023.
 */

enum class NotificationScreenType(val value: ItemChoose) {
    MessageScreen (
        ItemChoose(
            id = 1,
            name = "Tin nhắn",
            score = -1
        )
    ),
    NotificationScreen (
        ItemChoose(
            id = 2,
            name = "Thông báo",
            score = -3
        )
    ),
}