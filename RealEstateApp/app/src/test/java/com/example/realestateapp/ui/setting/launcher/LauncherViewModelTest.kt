package com.example.realestateapp.ui.setting.launcher

import com.example.realestateapp.MainDispatcherRule
import com.example.realestateapp.data.fake.FakeAppRepository
import com.example.realestateapp.data.models.User
import com.example.realestateapp.util.ConstantTest
import com.example.realestateapp.util.Constants
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
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
        Dispatchers.setMain(UnconfinedTestDispatcher())
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
            val result = launch(UnconfinedTestDispatcher()) { uiEffect.collect() }
            assertEquals(LauncherUiEffect.SignInSuccess, uiEffect.value)
            assertEquals(getUser().value, User(email = ConstantTest.DefaultTestLaunchValue.email))
            result.cancel()
        }
    }

    @Test
    fun `signInUserFail$app_debug`() = runTest {
        launcherViewModel.run {
            email.value = ConstantTest.DefaultTestLaunchValue.wrongEmail
            password.value = ConstantTest.DefaultTestLaunchValue.password
            signInUser()
            val result = launch(UnconfinedTestDispatcher()) { uiEffect.collect() }
            assertEquals(LauncherUiEffect.Error, uiEffect.value)
            assertNull(getUser().value)
            result.cancel()
        }
    }

    @Test
    fun `signUpUser$app_debug`() = runTest {
        launcherViewModel.run {
            email.value = ConstantTest.DefaultTestLaunchValue.email
            password.value = ConstantTest.DefaultTestLaunchValue.password
            signUpUser("Test", "0123456789")
            val result = launch(UnconfinedTestDispatcher()) { uiEffect.collect() }
            assertEquals(LauncherUiEffect.SignUpSuccess, uiEffect.value)
            result.cancel()
        }
    }

    @Test
    fun `signUpUserFail$app_debug`() = runTest {
        launcherViewModel.run {
            email.value = ConstantTest.DefaultTestLaunchValue.email
            password.value = ConstantTest.DefaultTestLaunchValue.password
            signUpUser("    ", "0123456789")
            val resultFail = launch(UnconfinedTestDispatcher()) { uiEffect.collect() }
            assertEquals(LauncherUiEffect.Error, uiEffect.value)
            resultFail.cancel()
        }
    }

    @Test
    fun `validEmail$app_debug`() {
        launcherViewModel.run {
            assertEquals(validEmail("tuyen.dang@team.enouvo.com"), "")
            assertEquals(validEmail("tuyen.dang@gmail.com"), "")
            assertEquals(validEmail("tuyen.dang "), "")
            firstClick.value = false
            assertEquals(validEmail("tuyen.dang "), Constants.ValidData.INVALID_EMAIL)
        }
    }

    @Test
    fun `validPassWord$app_debug`() {
        launcherViewModel.run {
            assertEquals(validPassWord("Test@123"), "")
            assertEquals(validPassWord("TTTTT123"), "")
            firstClick.value = false
            assertEquals(validPassWord("TTTTT123"), Constants.ValidData.INVALID_PASSWORD)
            assertEquals(validPassWord("test123"), Constants.ValidData.INVALID_PASSWORD)
        }
    }
}