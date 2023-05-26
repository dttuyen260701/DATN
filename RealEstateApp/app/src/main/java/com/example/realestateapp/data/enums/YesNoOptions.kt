package com.example.realestateapp.data.enums

import com.example.realestateapp.data.models.ItemChoose

/**
 * Created by tuyen.dang on 5/27/2023.
 */

enum class YesNoOptions(val value: ItemChoose) {
    YES(
        ItemChoose(
            id = 1,
            name = "Có",
            score = 1
        )
    ),
    No(
        ItemChoose(
            id = 0,
            name = "Không",
            score = 0
        )
    )
}