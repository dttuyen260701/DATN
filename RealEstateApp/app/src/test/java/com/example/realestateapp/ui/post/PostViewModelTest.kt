package com.example.realestateapp.ui.post

import com.example.realestateapp.MainDispatcherRule
import com.example.realestateapp.data.fake.FakeAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var fakeAppRepository: FakeAppRepository = FakeAppRepository()

    private lateinit var postViewModel: PostViewModel
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        postViewModel = PostViewModel(fakeAppRepository)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `updatePost$app_debug`() {
    }

    @Test
    fun `createPost$app_debug`() {
    }

    @Test
    fun `getRealEstateDetail$app_debug`() {
    }

    @Test
    fun `getPredictPrice$app_debug`() {
    }

    @Test
    fun `getTypes$app_debug`() {
    }

    @Test
    fun `getPosts$app_debug`() {
    }
}