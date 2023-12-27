package com.m4ykey.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m4ykey.data.domain.model.album.Items
import com.m4ykey.data.mappers.toItems
import com.m4ykey.data.remote.api.AlbumApi
import com.m4ykey.data.remote.interceptor.SpotifyInterceptor
import retrofit2.HttpException

class NewReleasePagingSource(
    private val api : AlbumApi,
    private val interceptor : SpotifyInterceptor
) : PagingSource<Int, Items>() {
    override fun getRefreshKey(state: PagingState<Int, Items>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Items> {
        return try {
            val page  = params.key ?: 0
            val limit = params.loadSize.coerceIn(1, 50)

            val response = api.getNewReleases(
                limit = limit,
                offset = page * limit,
                token = "Bearer ${interceptor.getAccessToken()}"
            ).albums.items.map { it.toItems() }

            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        } catch (e : HttpException) {
            LoadResult.Error(e)
        }
    }
}