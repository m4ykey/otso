package com.m4ykey.data.remote.interceptor

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.m4ykey.data.BuildConfig.SPOTIFY_CLIENT_ID
import com.m4ykey.data.BuildConfig.SPOTIFY_CLIENT_SECRET
import com.m4ykey.data.remote.api.spotify.SAuthApi
import com.m4ykey.data.remote.interceptor.token.TokenProvider
import com.m4ykey.data.remote.interceptor.token.fetchAccessToken
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SpotifyTokenProvider @Inject constructor(
    private val api : SAuthApi,
    private val dataStore : DataStore<Preferences>
) : TokenProvider {

    private val accessTokenKey = stringPreferencesKey("access_token")
    private val expireKey = longPreferencesKey("expire_token")

    private suspend fun saveAccessToken(accessToken: String, expireTime: Long) {
        dataStore.edit { preferences ->
            preferences[accessTokenKey] = accessToken
            preferences[expireKey] = expireTime
        }
    }

    override suspend fun getAccessToken(): String {
        val cachedToken = dataStore.data.first()[accessTokenKey]
        val expireTime = dataStore.data.first()[expireKey] ?: 0L

        return if (!cachedToken.isNullOrBlank() && System.currentTimeMillis() < expireTime) {
            cachedToken
        } else {
            val newAccessToken = fetchAccessToken(
                api = api,
                clientSecret = SPOTIFY_CLIENT_SECRET,
                clientId = SPOTIFY_CLIENT_ID
            )

            val newExpireTime = System.currentTimeMillis() + 3600 * 1000
            saveAccessToken(newAccessToken, newExpireTime)

            newAccessToken
        }
    }
}