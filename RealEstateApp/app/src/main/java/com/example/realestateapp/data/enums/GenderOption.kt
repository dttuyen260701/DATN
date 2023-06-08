package com.example.realestateapp.data.enums

import com.example.realestateapp.data.models.ItemChoose

/**
 * Created by tuyen.dang on 6/8/2023.
 */

enum class GenderOption(val value: ItemChoose) {
    MALE(
        ItemChoose(
            id = 1,
            name = "Nam",
            score = -1
        )
    ),
    FEMALE(
        ItemChoose(
            id = 0,
            name = "Nữ",
            score = -1
        )
    ),
}