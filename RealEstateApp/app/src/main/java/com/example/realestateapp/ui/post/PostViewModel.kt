package com.example.realestateapp.ui.post

import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by tuyen.dang on 5/4/2023.
 */

@HiltViewModel
class PostViewModel @Inject constructor(
    appRepository: AppRepository
) : BaseViewModel() {

}
