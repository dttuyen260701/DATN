package com.example.realestateapp

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.realestateapp.data.repository.ViewDataRepository
import com.example.realestateapp.ui.MainActivity
import com.example.realestateapp.ui.setting.SettingScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class AppTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun setting_Screen_Test() {
        composeTestRule.run {
            activity.setContent {
                SettingScreen(
                    listSettingButton = ViewDataRepository.getListSettingSignOut(),
                    user = null,
                    onEditClick = {},
                    onSignInClick = {},
                    onSignUpClick = {},
                    onPolicyClick = {},
                    onAboutUsClick = {},
                    onChangePassClick = {},
                    onSignOutListener = {}
                )
            }

            onNodeWithText(activity.getString(R.string.settingSignInTitle)).assertExists()
        }
    }
}
