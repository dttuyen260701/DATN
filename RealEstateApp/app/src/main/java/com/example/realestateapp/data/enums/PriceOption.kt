package com.example.realestateapp.data.enums

import com.example.realestateapp.data.models.ItemChoose

enum class PriceOption(val value: ItemChoose) {
    Below500M(
        ItemChoose(
            id = 0,
            name = "Dưới 500 Triệu",
            score = 500
        )
    ),
    From500Mto1B(
        ItemChoose(
            id = 500,
            name = "500 Triệu - 1 Tỷ",
            score = 1000
        )
    ),
    From1Bto2B(
        ItemChoose(
            id = 1000,
            name = "1 Tỷ - 2 Tỷ",
            score = 2000
        )
    ),
    From2Bto3B(
        ItemChoose(
            id = 2000,
            name = "2 Tỷ - 3 Tỷ",
            score = 3000
        )
    ),
    From3Bto5B(
        ItemChoose(
            id = 3000,
            name = "3 Tỷ - 5 Tỷ",
            score = 5000
        )
    ),
    From5Bto7B(
        ItemChoose(
            id = 5000,
            name = "5 Tỷ - 7 Tỷ",
            score = 7000
        )
    ),
    From7Bto10B(
        ItemChoose(
            id = 7000,
            name = "7 Tỷ - 10 Tỷ",
            score = 10000
        )
    ),
    From10Bto20B(
        ItemChoose(
            id = 10000,
            name = "10 Tỷ - 20 Tỷ",
            score = 20000
        )
    ),
    From20B(
        ItemChoose(
            id = 20000,
            name = "Trên 20 Tỷ",
            score = 99999
        )
    )
}
