package com.m4ykey.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.m4ykey.core.composable.LoadingMaxWidth
import com.m4ykey.core.helpers.showToast

@Composable
fun <T : Any> ItemColumnList(
    modifier : Modifier = Modifier,
    onItemClick : (T) -> Unit,
    itemList : LazyPagingItems<T>?,
    itemContent : @Composable (item : T, Modifier) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(
            count = itemList?.itemCount ?: 0,
            key = itemList?.itemKey { it.hashCode() }
        ) { index ->
            val item = itemList!![index]
            if (item != null) {
                itemContent(item, Modifier.clickable { onItemClick(item) })
            }
        }

        item {
            when (val appendState = itemList?.loadState?.append) {
                is LoadState.Loading -> { LoadingMaxWidth() }
                is LoadState.Error -> { showToast(LocalContext.current, "Error $appendState") }
                is LoadState.NotLoading -> Unit
                else -> Unit
            }
        }
        item {
            when (val refreshState = itemList?.loadState?.refresh) {
                is LoadState.Loading -> { LoadingMaxWidth() }
                is LoadState.Error -> { showToast(LocalContext.current, "Error $refreshState") }
                is LoadState.NotLoading -> Unit
                else -> Unit
            }
        }
    }
}