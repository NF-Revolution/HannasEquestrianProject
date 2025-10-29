package com.nfrevolution.hannasequestrianproject.home.data.repository

import com.nfrevolution.hannasequestrianproject.home.data.mapper.HomeConfigMapper
import com.nfrevolution.hannasequestrianproject.home.data.service.HomeService
import com.nfrevolution.hannasequestrianproject.home.domain.entity.HomeConfig
import com.nfrevolution.hannasequestrianproject.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory
internal class HomeRepositoryImpl(
    private val homeService: HomeService,
    private val homeConfigMapper: HomeConfigMapper,
) : HomeRepository {
    override suspend fun getHomeConfig(): Result<HomeConfig> = runCatching {
        val dto = homeService.getHomeConfig()
        homeConfigMapper.toDomain(dto)
    }
}
