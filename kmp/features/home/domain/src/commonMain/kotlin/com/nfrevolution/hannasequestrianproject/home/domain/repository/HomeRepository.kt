package com.nfrevolution.hannasequestrianproject.home.domain.repository

import com.nfrevolution.hannasequestrianproject.home.domain.entity.HomeConfig

public interface HomeRepository {
    public suspend fun getHomeConfig(): Result<HomeConfig>
}
