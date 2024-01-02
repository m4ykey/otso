package com.m4ykey.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.mappers.toItems
import com.m4ykey.data.remote.api.AlbumApi
import com.m4ykey.data.remote.interceptor.SpotifyInterceptor

class NewReleasePagingSource(
    private val api : AlbumApi,
    private val interceptor : SpotifyInterceptor
) : PagingSource<Int, Items>() {
    override fun getRefreshKey(state: PagingState<Int, Items>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { closestPage ->
                closestPage.nextKey ?: closestPage.prevKey
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Items> {
        return try {
            val page = params.key ?: 0
            val limit = params.loadSize.coerceIn(1, 50)

            val response = api.getNewReleases(
                limit = limit,
                offset = page * limit,
                token = "Bearer ${interceptor.getAccessToken()}"
            ).albums

            val prevKey = if (page > 0) page - 1 else null
            val nextKey = if (response.next.isNullOrEmpty()) null else page + 1

            LoadResult.Page(
                data = response.items.map { it.toItems() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}