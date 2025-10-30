package com.nfrevolution.hannasequestrianproject.home.presentation

import com.composegears.tiamat.navigation.NavDestination
import com.nfrevolution.hannasequestrianproject.home.domain.entity.HomeConfig
import com.nfrevolution.hannasequestrianproject.home.domain.usecase.GetHomeConfigUseCase
import com.nfrevolution.hannasequestrianproject.navigation.featureProvider.FeatureProvider
import com.nfrevolution.hannasequestrianproject.navigationdrawer.domain.drawerNavigator.DrawerNavigator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import pro.respawn.flowmvi.dsl.collect
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeViewModelTest {

    private lateinit var mockDrawerNavigator: DrawerNavigator
    private lateinit var mockFeatureProvider: FeatureProvider
    private lateinit var mockGetHomeConfigUseCase: GetHomeConfigUseCase
    private lateinit var mockHorsesDestination: NavDestination<Unit>
    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val testVideoUrl = "https://example.com/video.mp4"
    private val testHomeConfig = HomeConfig(introVideoUrl = testVideoUrl)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockDrawerNavigator = mockk(relaxed = true)
        mockHorsesDestination = mockk(relaxed = true)
        mockFeatureProvider = mockk(relaxed = true) {
            every { horses } returns mockHorsesDestination
        }
        mockGetHomeConfigUseCase = mockk()
        viewModel = HomeViewModel(
            drawerNavigator = mockDrawerNavigator,
            featureProvider = mockFeatureProvider,
            getHomeConfigUseCase = mockGetHomeConfigUseCase
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should initialize with ConfigLoading state`() = runTest {
        // Given
        coEvery { mockGetHomeConfigUseCase() } returns Result.success(testHomeConfig)
        val results = mutableListOf<HomeUiState>()

        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        advanceUntilIdle()

        // Then
        assertTrue(results.first() is HomeUiState.ConfigLoading)
        job.cancel()
    }

    @Test
    fun `should load config successfully and transition to VideoLoading state`() = runTest {
        // Given
        coEvery { mockGetHomeConfigUseCase() } returns Result.success(testHomeConfig)

        val results = mutableListOf<HomeUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When
        viewModel.store.intent(HomeIntent.LoadConfig)
        advanceUntilIdle()

        // Then
        val videoLoadingState = results.last() as HomeUiState.VideoLoading
        assertEquals(testVideoUrl, videoLoadingState.videoUrl)
        job.cancel()
    }

    @Test
    fun `should handle config loading failure and transition to Error state`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { mockGetHomeConfigUseCase() } returns Result.failure(Exception(errorMessage))

        val results = mutableListOf<HomeUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When
        viewModel.store.intent(HomeIntent.LoadConfig)
        advanceUntilIdle()

        // Then
        val errorState = results.last() as HomeUiState.Error
        assertEquals(errorMessage, errorState.message)
        job.cancel()
    }

    @Test
    fun `should handle config loading failure with unknown error message`() = runTest {
        // Given
        coEvery { mockGetHomeConfigUseCase() } returns Result.failure(Exception())

        val results = mutableListOf<HomeUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When
        viewModel.store.intent(HomeIntent.LoadConfig)
        advanceUntilIdle()

        // Then
        val errorState = results.last() as HomeUiState.Error
        assertEquals("Unknown error", errorState.message)
        job.cancel()
    }

    @Test
    fun `should transition from VideoLoading to Success when video is loaded`() = runTest {
        // Given
        coEvery { mockGetHomeConfigUseCase() } returns Result.success(testHomeConfig)

        val results = mutableListOf<HomeUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When
        viewModel.store.intent(HomeIntent.LoadConfig)
        advanceUntilIdle()
        viewModel.store.intent(HomeIntent.OnVideoLoaded)
        advanceUntilIdle()

        val expectedList = listOf(
            HomeUiState.VideoLoading(testVideoUrl),
            HomeUiState.Success(testVideoUrl),
        )

        // Then
        assertEquals(expectedList, results)
        job.cancel()
    }

    @Test
    fun `should not transition to Success if not in VideoLoading state`() = runTest {
        // Given
        coEvery { mockGetHomeConfigUseCase() } returns Result.success(testHomeConfig)

        val results = mutableListOf<HomeUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When - send OnVideoLoaded without loading config first
        viewModel.store.intent(HomeIntent.OnVideoLoaded)
        advanceUntilIdle()

        // Then - should remain in ConfigLoading state
        assertTrue(results.last() is HomeUiState.ConfigLoading)
        job.cancel()
    }

    @Test
    fun `should navigate to horses when box is clicked`() = runTest {
        // Given
        coEvery { mockGetHomeConfigUseCase() } returns Result.success(testHomeConfig)

        // When
        viewModel.store.intent(HomeIntent.OnBoxClicked)
        advanceUntilIdle()

        // Then
        verify { mockDrawerNavigator.navigateTo(mockHorsesDestination) }
    }

    @Test
    fun `should transition from Success to VideoLoading when screen is disposed`() = runTest {
        // Given
        coEvery { mockGetHomeConfigUseCase() } returns Result.success(testHomeConfig)

        val results = mutableListOf<HomeUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When - load config, mark video as loaded, then dispose
        viewModel.store.intent(HomeIntent.LoadConfig)
        advanceUntilIdle()
        viewModel.store.intent(HomeIntent.OnVideoLoaded)
        advanceUntilIdle()
        viewModel.store.intent(HomeIntent.OnScreenDisposed)
        advanceUntilIdle()

        // Then
        val videoLoadingState = results.last() as HomeUiState.VideoLoading
        assertEquals(testVideoUrl, videoLoadingState.videoUrl)
        job.cancel()
    }

    @Test
    fun `should transition to ConfigLoading when screen is disposed from non-Success state`() =
        runTest {
            // Given
            val errorMessage = "Test error"
            coEvery { mockGetHomeConfigUseCase() } returns Result.failure(Exception(errorMessage))

            val results = mutableListOf<HomeUiState>()

            val job = launch(testDispatcher) {
                viewModel.store.collect {
                    states.toList(results)
                }
            }

            // When - load config (which fails), then dispose
            viewModel.store.intent(HomeIntent.LoadConfig)
            advanceUntilIdle()
            viewModel.store.intent(HomeIntent.OnScreenDisposed)
            advanceUntilIdle()

            // Then
            assertTrue(results.last() is HomeUiState.ConfigLoading)
            job.cancel()
        }

    @Test
    fun `should maintain videoUrl throughout state transitions`() = runTest {
        // Given
        coEvery { mockGetHomeConfigUseCase() } returns Result.success(testHomeConfig)

        val results = mutableListOf<HomeUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When - complete full lifecycle
        viewModel.store.intent(HomeIntent.LoadConfig)
        advanceUntilIdle()
        viewModel.store.intent(HomeIntent.OnVideoLoaded)
        advanceUntilIdle()
        viewModel.store.intent(HomeIntent.OnScreenDisposed)
        advanceUntilIdle()

        // Then - videoUrl should be consistent
        val videoLoadingStates = results.filterIsInstance<HomeUiState.VideoLoading>()
        val successStates = results.filterIsInstance<HomeUiState.Success>()

        videoLoadingStates.forEach { state ->
            assertEquals(testVideoUrl, state.videoUrl)
        }
        successStates.forEach { state ->
            assertEquals(testVideoUrl, state.videoUrl)
        }
        job.cancel()
    }
}
