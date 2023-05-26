package com.example.realestateapp.data.enums

import com.example.realestateapp.data.models.ItemChoose

/**
 * Created by tuyen.dang on 5/27/2023.
 */

enum class Juridical(val value: ItemChoose) {
    CertificateOfLand (
        ItemChoose(
            id = 1,
            name = "Sổ đỏ/Sổ hồng",
            score = -1
        )
    ),
    Attestation (
        ItemChoose(
            id = 2,
            name = "Giấy tờ hợp lệ",
            score = -3
        )
    ),
    BusinessLicense (
        ItemChoose(
            id = 3,
            name = "Giấy phép kinh doanh",
            score = -2
        )
    )
}
