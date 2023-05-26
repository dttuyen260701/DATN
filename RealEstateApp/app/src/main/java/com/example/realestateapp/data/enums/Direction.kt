package com.example.realestateapp.data.enums

import com.example.realestateapp.data.models.ItemChoose

/**
 * Created by tuyen.dang on 5/27/2023.
 */

enum class Direction( val value: ItemChoose){
    South(
        ItemChoose(
            id = 1,
            name = "Nam",
            score = -2
        )
    ),
    East(
        ItemChoose(
            id = 2,
            name = "Đông",
            score = -6
        )
    ),
    North(
        ItemChoose(
            id = 3,
            name = "Bắc",
            score = -7
        )
    ),
    West(
        ItemChoose(
            id = 6,
            name = "Tây",
            score = -1
        )
    ),
    SouthEast(
        ItemChoose(
            id = 5,
            name = "Đông Nam",
            score = -3
        )
    ),
    Southwest(
        ItemChoose(
            id = 7,
            name = "Tây Nam",
            score = -8
        )
    ),
    NorthEast(
        ItemChoose(
            id = 8,
            name = "Đông Bắc",
            score = -5
        )
    ),
    NorthWest(
        ItemChoose(
            id = 4,
            name = "Tây Bắc",
            score = -4
        )
    )
}
