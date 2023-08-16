package com.example.realestateapp.ui.setting.profile

import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import com.example.realestateapp.ConstantTestUI
import com.example.realestateapp.R
import com.example.realestateapp.data.models.User
import com.example.realestateapp.findElementByStringRes
import com.example.realestateapp.findElementByText
import com.example.realestateapp.ui.MainActivity
import com.example.realestateapp.util.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@HiltAndroidTest
class ProfileScreenKtTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun profileScreen_Test() {
        val name = mutableStateOf("")
        val nameError = mutableStateOf("")
        val genderError = mutableStateOf("")
        val addressError = mutableStateOf("")
        var comboBoxChosen = ""
        composeTestRule.apply {

            activity.run {
                setContent {
                    ProfileScreen(
                        onBackClick = { },
                        isUploading = true,
                        user = ConstantTestUI.DefaultTestValue.DEFAULT_USER,
                        imgUrl = User().imgUrl ?: "",
                        onImageUserClick = { },
                        name = name.value,
                        onNameChange = { name.value = it },
                        nameError = nameError.value,
                        onComboBoxClick = {
                            comboBoxChosen = it
                        },
                        onClearData = { },
                        dateChosen = "01/01/2023",
                        genderChosen = ConstantTestUI.DefaultTestValue.DEFAULT_ITEM_CHOOSE,
                        genderError = genderError.value,
                        addressDetail = "",
                        addressError = addressError.value,
                        isEnableSubmit = true,
                        onSubmitClick = {
                            nameError.value = getString(R.string.nameError)
                            genderError.value = getString(
                                R.string.mandatoryError,
                                getString(R.string.genderTitle)
                            )
                            addressError.value = getString(
                                R.string.mandatoryError,
                                getString(R.string.addressTitle)
                            )
                        }
                    )
                }

                findElementByStringRes(R.string.nameTitle).performTextInput(activity.getString(R.string.latestTitle))
                findElementByStringRes(R.string.latestTitle).assertExists()
                Espresso.closeSoftKeyboard()

                findElementByStringRes(R.string.phoneTitle)

                findElementByText("01/01/2023").assertExists()
                assertEquals(Constants.DefaultField.FIELD_DATE, comboBoxChosen)

                findElementByText(ConstantTestUI.DefaultTestValue.DEFAULT_ITEM_CHOOSE.name).assertExists()
                assertEquals(Constants.DefaultField.FIELD_GENDER, comboBoxChosen)

                findElementByStringRes(R.string.addressHint).assertExists()
                assertEquals(Constants.DefaultField.FIELD_ADDRESS, comboBoxChosen)

                findElementByStringRes(R.string.updateTitle).assertExists()

                onNode(hasText(getString(R.string.nameError))).assertExists()
                onNode(
                    hasText(getString(R.string.mandatoryError, getString(R.string.genderTitle)))
                ).assertExists()
                onNode(
                    hasText(getString(R.string.mandatoryError, getString(R.string.addressTitle)))
                ).assertExists()
            }
        }
    }
}
