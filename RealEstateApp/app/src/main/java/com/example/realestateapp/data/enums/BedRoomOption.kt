package com.example.realestateapp.data.enums

import com.example.realestateapp.data.models.ItemChoose

/**
 * Created by tuyen.dang on 5/27/2023.
 */

enum class BedRoomOption(val value: ItemChoose) {
    Below3(
        ItemChoose(
            id = 0,
            name = "Ít hơn 3 phòng",
            score = 3
        )
    ),
    From3To5(
        ItemChoose(
            id = 3,
            name = "3 phòng - 5 phòng",
            score = 5
        )
    ),
    From5To7(
        ItemChoose(
            id = 5,
            name = "5 phòng - 7 phòng",
            score = 7
        )
    ),
    From7To10(
        ItemChoose(
            id = 7,
            name = "7 phòng - 10 phòng",
            score = 10
        )
    ),
    From10(
        ItemChoose(
            id = 10,
            name = "Trên 10 phòng",
            score = 9999999
        )
    )
}
