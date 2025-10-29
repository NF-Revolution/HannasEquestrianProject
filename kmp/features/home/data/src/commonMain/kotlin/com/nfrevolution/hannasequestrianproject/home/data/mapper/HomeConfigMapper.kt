package com.nfrevolution.hannasequestrianproject.home.data.mapper

import com.nfrevolution.hannasequestrianproject.home.data.dto.HomeConfigDto
import com.nfrevolution.hannasequestrianproject.home.domain.entity.HomeConfig
import com.nfrevolution.hannasequestrianproject.network.util.buildStorageUrl
import org.koin.core.annotation.Factory

@Factory
internal class HomeConfigMapper {
    fun toDomain(dto: HomeConfigDto): HomeConfig {
        val fullUrl = dto.introVideoPath.buildStorageUrl()
        return HomeConfig(
            introVideoUrl = fullUrl,
        )
    }
}
