package com.nfrevolution.hannasequestrianproject.network.di

import com.nfrevolution.hannasequestrianproject.network.client.createFirebaseDatabaseClient
import io.ktor.client.HttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.nfrevolution.hannasequestrianproject.network")
public class NetworkModule {

    @Single
    internal fun databaseClient(): HttpClient = createFirebaseDatabaseClient()
}
