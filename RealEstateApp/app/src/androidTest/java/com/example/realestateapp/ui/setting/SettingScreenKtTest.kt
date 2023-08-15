package com.example.realestateapp.ui.setting

import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.example.realestateapp.R
import com.example.realestateapp.data.models.User
import com.example.realestateapp.data.repository.ViewDataRepository
import com.example.realestateapp.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*


@HiltAndroidTest
class SettingScreenKtTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun buttonSetting(@StringRes title: Int) = composeTestRule.run {
        onNode(hasText(activity.getString(title)) and hasClickAction()).apply {
            performClick()
        }
    }

    @Test
    fun settingScreenSignOuted_Test() {
        var testResult = R.string.settingSignInTitle
        composeTestRule.apply {
            activity.setContent {
                SettingScreen(
                    listSettingButton = ViewDataRepository.getListSettingSignOut(),
                    user = null,
                    onEditClick = {},
                    onSignInClick = { testResult = 1 },
                    onSignUpClick = { testResult = 2 },
                    onPolicyClick = { testResult = 3 },
                    onAboutUsClick = { testResult = 4 },
                    onChangePassClick = {},
                    onSignOutListener = {}
                )
            }

            buttonSetting(R.string.settingSignInTitle).assertExists()
            assert(testResult == 1)
            buttonSetting(R.string.settingSignUpTitle).assertExists()
            assert(testResult == 2)
            buttonSetting(R.string.settingPolicyTitle).assertExists()
            assert(testResult == 3)
            buttonSetting(R.string.settingAboutUsTitle).assertExists()
            assert(testResult == 4)
        }
    }

    @Test
    fun settingScreenSignIn_Test() {
        var testResult = R.string.settingSignInTitle
        composeTestRule.apply {
            activity.run {
                setContent {
                    SettingScreen(
                        listSettingButton = ViewDataRepository.getListSettingSignIn(),
                        user = User(
                            fullName = getString(R.string.typesTitle),
                            email = getString(R.string.btnSubmitTitle)
                        ),
                        onEditClick = { testResult = 0 },
                        onSignInClick = { testResult = 1 },
                        onSignUpClick = { testResult = 2 },
                        onPolicyClick = { testResult = 3 },
                        onAboutUsClick = { testResult = 4 },
                        onChangePassClick = { testResult = 5 },
                        onSignOutListener = { testResult = 6 }
                    )
                }
            }

            buttonSetting(R.string.typesTitle).assertExists()
            assertEquals(0, testResult)
            buttonSetting(R.string.settingChangePassTitle).assertExists()
            assertEquals(5, testResult)
            buttonSetting(R.string.btnSubmitTitle).assertExists()
            assertEquals(0, testResult)
            buttonSetting(R.string.settingPolicyTitle).assertExists()
            assertEquals(3, testResult)
            buttonSetting(R.string.settingAboutUsTitle).assertExists()
            assertEquals(4, testResult)
            buttonSetting(R.string.settingSignOutTitle).assertExists()
            assertEquals(6, testResult)
        }
    }
}
