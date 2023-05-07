package com.example.realestateapp.ui

import androidx.compose.runtime.mutableStateOf
import com.example.realestateapp.data.models.User
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    appRepository: AppRepository
) : BaseViewModel() {
    companion object {
        private val user = mutableStateOf<User?>(null)

        internal fun getUser() = user
    }

}
