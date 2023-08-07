package com.example.realestateapp.extension

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NumberFormatExKtTest {
    @Test
    fun showFullNumberTest() {
        assertEquals(123.1200.showFullNumber(), "123.12")
        assertEquals(123.120100.showFullNumber(), "123.1201")
    }

    @Test
    fun formatToMoneyTest() {
        assertEquals(120000000.0.formatToMoney(), "120 Triệu")
        assertEquals(1200000000.0.formatToMoney(), "1,2 Tỷ")
        assertEquals(120_300_300.0.formatToMoney(), "120,3 Triệu")
    }

    @Test
    fun getDayMonthDisplay() {
        assertEquals(7.getDayMonthDisplay(), "07")
        assertEquals(10.getDayMonthDisplay(), "10")
    }
}