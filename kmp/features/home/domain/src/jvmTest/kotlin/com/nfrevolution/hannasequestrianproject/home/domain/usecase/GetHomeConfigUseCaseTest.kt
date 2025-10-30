package com.nfrevolution.hannasequestrianproject.home.domain.usecase

import com.nfrevolution.hannasequestrianproject.home.domain.entity.HomeConfig
import com.nfrevolution.hannasequestrianproject.home.domain.repository.HomeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class GetHomeConfigUseCaseTest {

    private lateinit var mockRepository: HomeRepository
    private lateinit var useCase: GetHomeConfigUseCase

    @BeforeTest
    fun setup() {
        mockRepository = mockk(relaxed = true)
        useCase = GetHomeConfigUseCase(repository = mockRepository)
    }

    @Test
    fun `should return success result with home config when repository returns success`() =
        runTest {
            // Arrange
            val expectedHomeConfig = HomeConfig(introVideoUrl = "https://example.com/video.mp4")
            coEvery { mockRepository.getHomeConfig() } returns Result.success(expectedHomeConfig)

            // Act
            val result: Result<HomeConfig> = useCase()

            // Assert
            assertTrue(result.isSuccess)
            assertEquals(expectedHomeConfig, result.getOrNull())
            coVerify(exactly = 1) { mockRepository.getHomeConfig() }
        }

    @Test
    fun `should return failure result when repository returns failure`() = runTest {
        // Arrange
        val expectedException = Exception("Network error")
        coEvery { mockRepository.getHomeConfig() } returns Result.failure(expectedException)

        // Act
        val result: Result<HomeConfig> = useCase()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        coVerify(exactly = 1) { mockRepository.getHomeConfig() }
    }

    @Test
    fun `should call repository only once when invoked`() = runTest {
        // Arrange
        val expectedHomeConfig = HomeConfig(introVideoUrl = "https://example.com/video.mp4")
        coEvery { mockRepository.getHomeConfig() } returns Result.success(expectedHomeConfig)

        // Act
        useCase()

        // Assert
        coVerify(exactly = 1) { mockRepository.getHomeConfig() }
    }
}
