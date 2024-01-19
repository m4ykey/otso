package com.m4ykey.ui.spotify.artist

import androidx.lifecycle.ViewModel
import com.m4ykey.data.domain.repository.ArtistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val repository: ArtistRepository
) : ViewModel() {

    suspend fun getArtistById(artistId : String) {

    }

}