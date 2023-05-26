package com.example.realestateapp.data.enums

import com.example.realestateapp.data.models.ItemChoose

/**
 * Created by tuyen.dang on 5/26/2023.
 */

enum class SquareOption(val value: ItemChoose) {
    Below50M(
        ItemChoose(
            id = 0,
            name = "Dưới 50 m²",
            score = 50
        )
    ),
    From50Mto100M(
        ItemChoose(
            id = 50,
            name = "50 m² - 100 m²",
            score = 100
        )
    ),
    From100Mto200M(
        ItemChoose(
            id = 100,
            name = "100 m² - 200 m²",
            score = 200
        )
    ),
    From200Mto300M(
        ItemChoose(
            id = 200,
            name = "200 m² - 300 m²",
            score = 300
        )
    ),
    From300Mto400M(
        ItemChoose(
            id = 300,
            name = "300 m² - 400 m²",
            score = 400
        )
    ),
    From400Mto500M(
        ItemChoose(
            id = 400,
            name = "400 m² - 500 m²",
            score = 500
        )
    ),
    From500M(
        ItemChoose(
            id = 500,
            name = "Trên 500 m²",
            score = 100000
        )
    )
}
