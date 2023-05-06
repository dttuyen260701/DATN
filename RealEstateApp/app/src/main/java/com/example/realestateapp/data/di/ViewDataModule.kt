package com.example.realestateapp.data.di

import com.example.realestateapp.R
import com.example.realestateapp.data.models.view.SettingButton
import com.example.realestateapp.designsystem.icon.AppIcon
import com.example.realestateapp.designsystem.icon.RealStateIcon
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

/**
 * Created by tuyen.dang on 5/6/2023.
 */

@Qualifier
annotation class SignInData

@Qualifier
annotation class SignOutData

@Module
@InstallIn(SingletonComponent::class)
object SettingButtonDataModule {
    @SignInData
    @Binds
    fun provideSettingButtons(): MutableList<SettingButton> {
        val listSettingButton = mutableListOf<SettingButton>()
        listSettingButton.run {
            add(
                SettingButton(
                    title = R.string.settingChangePassTitle,
                    leadingIcon = AppIcon.ImageVectorIcon(RealStateIcon.Lock)
                )
            )
            add(
                SettingButton(
                    title = R.string.settingPostSavedTitle,
                    leadingIcon = AppIcon.ImageVectorIcon(RealStateIcon.Lock)
                )
            )
            add(
                SettingButton(
                    title = R.string.settingChangePassTitle,
                    leadingIcon = AppIcon.ImageVectorIcon(RealStateIcon.Lock)
                )
            )
        }
        return listSettingButton
    }
}
