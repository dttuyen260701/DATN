package com.example.realestateapp

import androidx.annotation.StringRes
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.realestateapp.ui.MainActivity


internal fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.findElementByStringRes(@StringRes title: Int) =
    run {
        onNode(hasText(activity.getString(title)) and hasClickAction()).apply {
            performClick()
        }
    }


internal fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.findElementByText(title: String) =
    run {
        onNode(hasText(title) and hasClickAction()).apply {
            performClick()
        }
    }