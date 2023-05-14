package com.example.realestateapp.data.repository

import com.example.realestateapp.R
import com.example.realestateapp.data.models.view.SettingButton
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealEstateIcon

/**
 * Created by tuyen.dang on 5/7/2023.
 */

object ViewDataRepository {
    private val listSettingButtonSignIn = mutableListOf<SettingButton>()
    private val listSettingButtonSignOut = mutableListOf<SettingButton>()

    init {
        listSettingButtonSignIn.run {
            add(
                SettingButton(
                    title = R.string.settingChangePassTitle,
                    leadingIcon = AppIcon.ImageVectorIcon(RealEstateIcon.Lock)
                )
            )
            add(
                SettingButton(
                    title = R.string.settingPolicyTitle,
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Policy)
                )
            )
            add(
                SettingButton(
                    title = R.string.settingAboutUsTitle,
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.AboutUs)
                )
            )
            add(
                SettingButton(
                    title = R.string.settingSignOutTitle,
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.SignOut)
                )
            )
        }

        listSettingButtonSignOut.run {
            add(
                SettingButton(
                    title = R.string.settingSignInTitle,
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.SignIn)
                )
            )
            add(
                SettingButton(
                    title = R.string.settingSignUpTitle,
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.SignUp)
                )
            )
            add(
                SettingButton(
                    title = R.string.settingPolicyTitle,
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.Policy)
                )
            )
            add(
                SettingButton(
                    title = R.string.settingAboutUsTitle,
                    leadingIcon = AppIcon.DrawableResourceIcon(RealEstateIcon.AboutUs)
                )
            )
        }
    }

    fun getListSettingSignIn() = listSettingButtonSignIn

    fun getListSettingSignOut() = listSettingButtonSignOut
}
