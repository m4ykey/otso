package com.m4ykey.data.remote.interceptor

import com.m4ykey.data.BuildConfig
import com.m4ykey.data.remote.api.lyrics.GAuthApi
import com.m4ykey.data.remote.interceptor.token.TokenProvider
import com.m4ykey.data.remote.interceptor.token.fetchAccessToken
import javax.inject.Inject

class GeniusTokenProvider @Inject constructor(
    private val api : GAuthApi
) : TokenProvider {
    override suspend fun getAccessToken(): String {
        return fetchAccessToken(
            api = api,
            clientId = BuildConfig.GENIUS_CLIENT_ID,
            clientSecret = BuildConfig.GENIUS_CLIENT_SECRET
        )
    }
}