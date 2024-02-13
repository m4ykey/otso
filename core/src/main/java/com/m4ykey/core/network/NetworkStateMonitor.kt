package com.m4ykey.core.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NetworkStateMonitor @Inject constructor(
    private val connectivityManager: ConnectivityManager
) {

    private val _isInternetAvailable : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isInternetAvailable : StateFlow<Boolean> get() = _isInternetAvailable

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _isInternetAvailable.value = true
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            _isInternetAvailable.value = false
        }
    }

    fun startMonitoring() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun stopMonitoring() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}