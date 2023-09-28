package com.example.realestateapp.ui.setting.changepass

import com.example.realestateapp.MainDispatcherRule
import com.example.realestateapp.data.fake.FakeAppRepository
import com.example.realestateapp.data.models.User
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

@OptIn(ExperimentalCoroutinesApi::class)
class ChangePassViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var fakeAppRepository: FakeAppRepository = FakeAppRepository()

    private lateinit var changePassViewModel: ChangePassViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        changePassViewModel = ChangePassViewModel(fakeAppRepository)
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun `changePassword$app_debug`() = runTest {
        changePassViewModel.run {
            setUser(User(id = 1))
            oldPass.value = "Test@123"
            newPass.value = "Test@1234"
            changePassword()
            val result = launch(UnconfinedTestDispatcher()) { uiEffect.collect() }
            assertEquals(true, (uiEffect.value as? ChangePassUiEffect.ChangePassSuccess)?.data)
            result.cancel()
        }
    }

    @Test
    fun `changePasswordFail$app_debug`() = runTest {
        changePassViewModel.run {
            setUser(User(id = 1))
            oldPass.value = "Test@123"
            newPass.value = "Test@123"
            changePassword()
            val result = launch(UnconfinedTestDispatcher()) { uiEffect.collect() }
            assertEquals(false, (uiEffect.value as? ChangePassUiEffect.ChangePassSuccess)?.data)
            result.cancel()
        }
    }
}