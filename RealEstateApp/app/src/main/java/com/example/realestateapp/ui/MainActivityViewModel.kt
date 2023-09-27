package com.example.realestateapp.ui

import android.net.Uri
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import com.example.realestateapp.ui.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/3/2023.
 */

sealed class MainUiEffect : UiEffect() {
    object InitView : MainUiEffect()
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    appRepository: AppRepository
) : BaseViewModel<MainUiEffect>(appRepository) {
    override val uiEffectValue: MutableStateFlow<UiEffect> = MutableStateFlow(MainUiEffect.InitView)
    override val uiEffect: StateFlow<UiEffect> = uiEffectValue.asStateFlow()

    internal var uri: Uri? = null
}
