package com.m4ykey.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m4ykey.data.domain.model.album.tracks.TrackItem
import com.m4ykey.data.mappers.toTrackItem
import com.m4ykey.data.remote.api.music.AlbumApi
import com.m4ykey.data.remote.interceptor.SpotifyTokenProvider

class TrackListPagingSource(
    private val api : AlbumApi,
    private val interceptor: SpotifyTokenProvider,
    private val albumId : String
) : PagingSource<Int, TrackItem>() {
    override fun getRefreshKey(state: PagingState<Int, TrackItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { closestPage ->
                closestPage.nextKey ?: closestPage.prevKey
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrackItem> {
        return try {
            val page = params.key ?: 0
            val limit = params.loadSize.coerceIn(1, 50)

            val response = api.getAlbumTracks(
                limit = limit,
                offset = page * limit,
                token = "Bearer ${interceptor.getAccessToken()}",
                albumId = albumId
            )

            val prevKey = if (response.previous.isNullOrEmpty()) page - 1 else null
            val nextKey = if (response.next.isNullOrEmpty()) null else page + 1

            LoadResult.Page(
                data = response.items.map { it.toTrackItem() },
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}