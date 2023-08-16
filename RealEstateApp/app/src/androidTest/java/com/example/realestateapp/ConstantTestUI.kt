package com.example.realestateapp

import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.User

class ConstantTestUI {
    object DefaultTestValue {
        val DEFAULT_USER = User(
            id = 1,
            fullName = "Nguyen Van Test",
            email = "test@gmail.com"
        )
        val DEFAULT_ITEM_CHOOSE = ItemChoose(
            id = 1,
            name = "Test"
        )
    }
}