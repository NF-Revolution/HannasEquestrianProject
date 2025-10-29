package com.nfrevolution.hannasequestrianproject.home.data.service

import com.nfrevolution.hannasequestrianproject.home.data.dto.HomeConfigDto
import com.nfrevolution.hannasequestrianproject.network.service.BaseService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.annotation.Factory

@Factory
internal class HomeService(
    private val databaseClient: HttpClient,
) : BaseService() {
    suspend fun getHomeConfig(): HomeConfigDto = executeWithRetry {
        databaseClient.get("/home_config.json").body()
    }
}
