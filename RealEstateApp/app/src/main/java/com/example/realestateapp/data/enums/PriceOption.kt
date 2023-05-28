package com.example.realestateapp.data.enums

import com.example.realestateapp.data.models.ItemChoose

enum class PriceOption(val value: ItemChoose) {
    Below500M(
        ItemChoose(
            id = 0,
            name = "Dưới 500 Triệu",
            score = 500_000
        )
    ),
    From500Mto1B(
        ItemChoose(
            id = 500_000,
            name = "500 Triệu - 1 Tỷ",
            score = 1000_000
        )
    ),
    From1Bto2B(
        ItemChoose(
            id = 1000_000,
            name = "1 Tỷ - 2 Tỷ",
            score = 2000_000
        )
    ),
    From2Bto3B(
        ItemChoose(
            id = 2000_000,
            name = "2 Tỷ - 3 Tỷ",
            score = 3000_000
        )
    ),
    From3Bto5B(
        ItemChoose(
            id = 3000_000,
            name = "3 Tỷ - 5 Tỷ",
            score = 5000_000
        )
    ),
    From5Bto7B(
        ItemChoose(
            id = 5000_000,
            name = "5 Tỷ - 7 Tỷ",
            score = 7000_000
        )
    ),
    From7Bto10B(
        ItemChoose(
            id = 7000_000,
            name = "7 Tỷ - 10 Tỷ",
            score = 10000_000
        )
    ),
    From10Bto20B(
        ItemChoose(
            id = 10000_000,
            name = "10 Tỷ - 20 Tỷ",
            score = 20000_000
        )
    ),
    From20B(
        ItemChoose(
            id = 20000_000,
            name = "Trên 20 Tỷ",
            score = 99999_000
        )
    )
}
