package com.m4ykey.core.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkStateMonitor: NetworkStateMonitor
) : ViewModel() {

    private val _isInternetAvailable = MutableStateFlow(false)
    val isInternetAvailable : StateFlow<Boolean> get() = _isInternetAvailable

    init {
        networkStateMonitor.startMonitoring()
        observeNetworkState()
    }

    private fun observeNetworkState() {
        viewModelScope.launch {
            networkStateMonitor.isInternetAvailable.collect { isInternetAvailable ->
                _isInternetAvailable.value = isInternetAvailable
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        networkStateMonitor.stopMonitoring()
    }

}