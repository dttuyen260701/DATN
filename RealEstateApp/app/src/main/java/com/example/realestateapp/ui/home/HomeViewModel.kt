package com.example.realestateapp.ui.home

import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    val appRepository: AppRepository
) : BaseViewModel() {

}