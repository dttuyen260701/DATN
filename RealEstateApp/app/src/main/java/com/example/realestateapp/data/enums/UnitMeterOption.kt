package com.example.realestateapp.data.enums

import com.example.realestateapp.data.models.ItemChoose

/**
 * Created by tuyen.dang on 5/27/2023.
 */

enum class UnitMeterOption(val value: ItemChoose) {
    Below5M(
        ItemChoose(
            id = 0,
            name = "Dưới 5 m",
            score = 5
        )
    ),
    From5Mto10M(
        ItemChoose(
            id = 5,
            name = "5 m - 10 m",
            score = 10
        )
    ),
    From10Mto20M(
        ItemChoose(
            id = 10,
            name = "10 m - 20 m",
            score = 20
        )
    ),
    From20Mto30M(
        ItemChoose(
            id = 20,
            name = "20 m - 30 m",
            score = 30
        )
    ),
    From2M(
        ItemChoose(
            id = 30,
            name = "Trên 30 m",
            score = 100000
        )
    )
}
