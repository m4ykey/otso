package com.m4ykey.core.network

suspend fun <T> safeApiCall(apiCall : suspend () -> T) : Resource<T> {
    return try {
        Resource.Success(apiCall())
    } catch (e : Exception) {
        Resource.Error(
            data = null,
            message = e.localizedMessage ?: "An unexpected error occurred"
        )
    }
}