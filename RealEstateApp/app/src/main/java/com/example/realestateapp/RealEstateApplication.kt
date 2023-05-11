package com.example.realestateapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RealEstateApplication @Inject constructor() : Application() {}
