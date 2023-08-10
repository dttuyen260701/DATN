package com.example.realestateapp.ui.setting.launcher

import com.example.realestateapp.MainDispatcherRule
import com.example.realestateapp.data.fake.FakeAppRepository
import com.example.realestateapp.data.models.User
import com.example.realestateapp.util.ConstantTest
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Created by tuyen.dang on 8/10/2023.
 */

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class LauncherViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var fakeAppRepository: FakeAppRepository = FakeAppRepository()
    private lateinit var launcherViewModel: LauncherViewModel

    @BeforeEach
    fun setUp() {
        launcherViewModel = LauncherViewModel(HiltTestApplication(), fakeAppRepository)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `signInUser$app_debug`() = runTest {
        launcherViewModel.run {
            email.value = ConstantTest.DefaultTestLaunchValue.email
            password.value = ConstantTest.DefaultTestLaunchValue.password
            signInUser()
            val result = launch(UnconfinedTestDispatcher()) {
                uiState.collect {
                    assertEquals(it, LauncherUiState.SignInSuccess)
                    assertEquals(getUser().value, User(email = ConstantTest.DefaultTestLaunchValue.email))
                }
            }
            result.cancel()
        }
    }

    @Test
    fun `signUpUser$app_debug`() {
    }

    @Test
    fun `validEmail$app_debug`() {
    }

    @Test
    fun `validPassWord$app_debug`() {
    }
}