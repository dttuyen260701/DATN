package com.example.realestateapp.extension

/**
 * Created by tuyen.dang on 5/27/2023.
 */

internal fun String.handleAddressException() =
    this.replace("Hoà", "Hòa")
        .replace("Hòa Thuận Nam", "Hòa Thuận Tây")
 