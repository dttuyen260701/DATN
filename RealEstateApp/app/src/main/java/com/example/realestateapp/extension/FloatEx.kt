package com.example.realestateapp.extension

import com.example.realestateapp.util.Constants

/**
 * Created by tuyen.dang on 5/14/2023.
 */
 
internal fun Float.formatToMoney(): String {
    return try {
        var price = this
        var count = 0
        while (price >= 1000) {
            price/= 1000
            count += 1
        }
        "$price ${
            when(count) {
                1 -> Constants.PriceUnit.MILLION
                2 -> Constants.PriceUnit.BILLION
                else -> ""
            }
        }"
    } catch (e: Exception) {
        this.toString()
    }
}
