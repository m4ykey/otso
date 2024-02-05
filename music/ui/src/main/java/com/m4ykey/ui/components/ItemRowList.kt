package com.m4ykey.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T: Any> ItemRowList(
    modifier : Modifier = Modifier,
    itemList : List<T>?,
    onItemClick : (T) -> Unit,
    itemContent : @Composable (item : T, Modifier) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            count = itemList?.size ?: 0,
            key = { index -> itemList!![index].hashCode() }
        ) { index ->
            val item = itemList!![index]
            if (item != null) {
                itemContent(item, Modifier.clickable { onItemClick(item) })
            }
        }
    }
}