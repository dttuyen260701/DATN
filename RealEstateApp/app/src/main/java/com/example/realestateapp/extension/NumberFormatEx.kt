package com.example.realestateapp.extension

import com.example.realestateapp.util.Constants
import kotlin.math.round

/**
 * Created by tuyen.dang on 5/14/2023.
 */
 
internal fun Double.formatToMoney(): String {
    return try {
        var price = this
        var count = 0
        while (price >= 1000) {
            price /= 1000
            count += 1
        }
        "$price ${
            when (count) {
                1 -> Constants.PriceUnit.THOUSAND
                2 -> Constants.PriceUnit.MILLION
                3 -> Constants.PriceUnit.BILLION
                4 -> Constants.PriceUnit.THOUSAND_BILLION
                else -> ""
            }
        }".replace(".", ",")
    } catch (e: Exception) {
        this.toString()
    }
}

internal fun Int.formatToUnit(): String {
    return try {
        var view = this.toFloat()
        var count = 0
        while (view >= 1000) {
            view /= 1000
            count += 1
        }
        val tempView = if (round(view) == view) view.toInt() else view
        "$tempView${
            when (count) {
                1 -> Constants.PriceUnit.THOUSAND_CHAR
                2 -> Constants.PriceUnit.MILLION_CHAR
                3 -> Constants.PriceUnit.BILLION_CHAR
                else -> ""
            }
        }".replace(".", ",")
    } catch (e: Exception) {
        this.toString()
    }
}
