package com.m4ykey.core.paging

interface Paging<Key, Item> {
    fun resetItems()
    suspend fun loadNextItem()
}