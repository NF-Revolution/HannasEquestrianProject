package com.nfrevolution.hannasequestrianproject.network.service

import kotlinx.coroutines.delay

/**
 * Abstract base service providing automatic retry logic for network operations.
 *
 * All service classes should extend this class to benefit from built-in retry mechanism.
 * Operations are automatically retried up to 3 times with a 1-second delay between attempts.
 *
 */
private const val MAX_RETRIES = 3
private const val RETRY_DELAY_MS = 1000L

public abstract class BaseService {
    /**
     * Executes the given operation with automatic retry logic.
     *
     * @param operation The suspend function to execute with retry logic
     * @return The result of the operation
     * @throws Exception The last exception encountered if all retry attempts fail
     */
    protected suspend fun <T> executeWithRetry(
        operation: suspend () -> T
    ): T {
        var lastException: Exception? = null

        repeat(MAX_RETRIES) { attempt ->
            try {
                return operation()
            } catch (e: Exception) {
                lastException = e
                if (attempt < MAX_RETRIES - 1) {
                    delay(RETRY_DELAY_MS)
                }
            }
        }

        throw lastException ?: IllegalStateException("Operation failed after $MAX_RETRIES retries")
    }
}
