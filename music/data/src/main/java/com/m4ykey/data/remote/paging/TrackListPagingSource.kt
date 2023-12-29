package com.m4ykey.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m4ykey.data.domain.model.album.tracks.TrackItem
import com.m4ykey.data.mappers.toTrackItem
import com.m4ykey.data.remote.api.AlbumApi
import com.m4ykey.data.remote.interceptor.SpotifyInterceptor

class TrackListPagingSource(
    private val api : AlbumApi,
    private val interceptor: SpotifyInterceptor,
    private val albumId : String
) : PagingSource<Int, TrackItem>() {
    override fun getRefreshKey(state: PagingState<Int, TrackItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
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
            ).items.map { it.toTrackItem() }

            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )

        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}