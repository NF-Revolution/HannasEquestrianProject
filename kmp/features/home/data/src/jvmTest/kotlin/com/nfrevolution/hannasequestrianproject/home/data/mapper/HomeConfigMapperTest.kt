package com.nfrevolution.hannasequestrianproject.home.data.mapper

import com.nfrevolution.hannasequestrianproject.home.data.dto.HomeConfigDto
import com.nfrevolution.hannasequestrianproject.home.domain.entity.HomeConfig
import com.nfrevolution.hannasequestrianproject.network.util.buildStorageUrl
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class HomeConfigMapperTest {

    private lateinit var mapper: HomeConfigMapper

    @BeforeTest
    fun setup() {
        mapper = HomeConfigMapper()
        mockkStatic("com.nfrevolution.hannasequestrianproject.network.util.StorageUrlBuilderKt")
    }

    @AfterTest
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should map dto to domain with correct url`() {
        // Arrange
        val inputDto = HomeConfigDto(introVideoPath = "videos/intro.mp4")
        val expectedUrl = "https://storage.googleapis.com/videos%2Fintro.mp4?alt=media"
        every { "videos/intro.mp4".buildStorageUrl() } returns expectedUrl

        // Act
        val result: HomeConfig = mapper.toDomain(inputDto)

        // Assert
        assertEquals(expectedUrl, result.introVideoUrl)
    }

    @Test
    fun `should handle empty path`() {
        // Arrange
        val inputDto = HomeConfigDto(introVideoPath = "")
        val expectedUrl = "https://storage.googleapis.com/?alt=media"
        every { "".buildStorageUrl() } returns expectedUrl

        // Act
        val result: HomeConfig = mapper.toDomain(inputDto)

        // Assert
        assertEquals(expectedUrl, result.introVideoUrl)
    }

    @Test
    fun `should handle path with multiple segments`() {
        // Arrange
        val inputDto = HomeConfigDto(introVideoPath = "assets/videos/intro/main.mp4")
        val expectedUrl =
            "https://storage.googleapis.com/assets%2Fvideos%2Fintro%2Fmain.mp4?alt=media"
        every { "assets/videos/intro/main.mp4".buildStorageUrl() } returns expectedUrl

        // Act
        val result: HomeConfig = mapper.toDomain(inputDto)

        // Assert
        assertEquals(expectedUrl, result.introVideoUrl)
    }
}
