package com.m4ykey.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m4ykey.data.domain.model.playlist.PlaylistItems
import com.m4ykey.data.mappers.toPlaylistItems
import com.m4ykey.data.remote.api.spotify.PlaylistApi
import com.m4ykey.data.remote.interceptor.SpotifyTokenProvider

class PlaylistPagingSource(
    private val api : PlaylistApi,
    private val interceptor : SpotifyTokenProvider
) : PagingSource<Int, PlaylistItems>() {

    override fun getRefreshKey(state: PagingState<Int, PlaylistItems>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { closestPage ->
                closestPage.nextKey ?: closestPage.prevKey
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaylistItems> {
        return try {
            val page = params.key ?: 0
            val limit = params.loadSize.coerceIn(1, 50)

            val response = api.getFeaturedPlaylists(
                limit = limit,
                offset = page * limit,
                token = "Bearer ${interceptor.getAccessToken()}"
            ).playlists

            val prevKey = if (response.previous.isNullOrEmpty()) page - 1 else null
            val nextKey = if (response.next.isNullOrEmpty()) null else page + 1

            LoadResult.Page(
                data = response.items.map { it.toPlaylistItems() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}