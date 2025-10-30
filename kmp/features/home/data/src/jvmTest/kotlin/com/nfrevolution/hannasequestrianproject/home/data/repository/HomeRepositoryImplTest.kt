package com.nfrevolution.hannasequestrianproject.home.data.repository

import com.nfrevolution.hannasequestrianproject.home.data.dto.HomeConfigDto
import com.nfrevolution.hannasequestrianproject.home.data.mapper.HomeConfigMapper
import com.nfrevolution.hannasequestrianproject.home.data.service.HomeService
import com.nfrevolution.hannasequestrianproject.home.domain.entity.HomeConfig
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class HomeRepositoryImplTest {

    private lateinit var mockHomeService: HomeService
    private lateinit var mockHomeConfigMapper: HomeConfigMapper
    private lateinit var repository: HomeRepositoryImpl

    @BeforeTest
    fun setup() {
        mockHomeService = mockk()
        mockHomeConfigMapper = mockk()
        repository = HomeRepositoryImpl(
            homeService = mockHomeService,
            homeConfigMapper = mockHomeConfigMapper
        )
    }

    @Test
    fun `should return success result with home config when service returns dto`() = runTest {
        // Arrange
        val inputDto = HomeConfigDto(introVideoPath = "videos/intro.mp4")
        val expectedHomeConfig = HomeConfig(introVideoUrl = "https://example.com/videos/intro.mp4")
        coEvery { mockHomeService.getHomeConfig() } returns inputDto
        every { mockHomeConfigMapper.toDomain(inputDto) } returns expectedHomeConfig

        // Act
        val result: Result<HomeConfig> = repository.getHomeConfig()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(expectedHomeConfig, result.getOrNull())
        coVerify(exactly = 1) { mockHomeService.getHomeConfig() }
        verify(exactly = 1) { mockHomeConfigMapper.toDomain(inputDto) }
    }

    @Test
    fun `should return failure result when service throws exception`() = runTest {
        // Arrange
        val expectedException = Exception("Network error")
        coEvery { mockHomeService.getHomeConfig() } throws expectedException

        // Act
        val result: Result<HomeConfig> = repository.getHomeConfig()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        coVerify(exactly = 1) { mockHomeService.getHomeConfig() }
        verify(exactly = 0) { mockHomeConfigMapper.toDomain(any()) }
    }

    @Test
    fun `should return failure result when mapper throws exception`() = runTest {
        // Arrange
        val inputDto = HomeConfigDto(introVideoPath = "videos/intro.mp4")
        val expectedException = Exception("Mapping error")
        coEvery { mockHomeService.getHomeConfig() } returns inputDto
        every { mockHomeConfigMapper.toDomain(inputDto) } throws expectedException

        // Act
        val result: Result<HomeConfig> = repository.getHomeConfig()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        coVerify(exactly = 1) { mockHomeService.getHomeConfig() }
        verify(exactly = 1) { mockHomeConfigMapper.toDomain(inputDto) }
    }
}
