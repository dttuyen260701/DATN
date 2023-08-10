package com.example.realestateapp.ui.setting.profile

import com.example.realestateapp.MainDispatcherRule
import com.example.realestateapp.data.fake.FakeAppRepository
import com.example.realestateapp.data.models.User
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var fakeAppRepository: FakeAppRepository = FakeAppRepository()

    private lateinit var profileViewModel: ProfileViewModel

    @BeforeEach
    fun setUp() {
        profileViewModel = ProfileViewModel(fakeAppRepository)
    }

    @Test
    fun `updateInformationUser$app_debug`() = runTest {
        profileViewModel.run {
            getInformationUser()
            val result = launch(UnconfinedTestDispatcher()) {
                uiState.collect {
                    if (it is ProfileUiState.UpdateInformationUserSuccess) {
                        assertEquals(
                            User(
                                id = 1,
                                fullName = "Testing Edit",
                                dateOfBirth = "07/07/07"
                            ),
                            (it as? ProfileUiState.UpdateInformationUserSuccess)?.data
                        )
                    }
                }
            }
            result.cancel()
        }
    }

    @Test
    fun `getInformationUser$app_debug`() = runTest {
        profileViewModel.run {
            getInformationUser()
            val result = launch(UnconfinedTestDispatcher()) {
                uiState.collect {
                    if (it is ProfileUiState.GetInformationUserSuccess) {
                        assertEquals(
                            User(),
                            (it as? ProfileUiState.GetInformationUserSuccess)?.data
                        )
                    }
                }
            }
            result.cancel()
        }
    }
}