package com.nfrevolution.hannasequestrianproject.home.domain.usecase

import com.nfrevolution.hannasequestrianproject.home.domain.entity.HomeConfig
import com.nfrevolution.hannasequestrianproject.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory
public class GetHomeConfigUseCase(
    private val repository: HomeRepository,
) {
    public suspend operator fun invoke(): Result<HomeConfig> = repository.getHomeConfig()
}
