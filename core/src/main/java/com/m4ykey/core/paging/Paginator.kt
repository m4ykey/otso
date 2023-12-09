package com.m4ykey.core.paging

class Paginator<Key, Item>(
    private val initialKey : Key,
    private inline val onLoadUpdated : (Boolean) -> Unit,
    private inline val onRequest : suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey : suspend (List<Item>) -> Key,
    private inline val onError : suspend (Throwable?) -> Unit,
    private inline val onSuccess : suspend (items: List<Item>, newKey : Key) -> Unit
) : Paging<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override fun resetItems() {
        currentKey = initialKey
    }

    override suspend fun loadNextItem() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        result.onSuccess { items ->
            if (items.isNotEmpty()) {
                currentKey = getNextKey(items)
                onSuccess(items, currentKey)
            } else {
                // No more items to load
                onLoadUpdated(false)
            }
        }
        result.onFailure {
            onError(it)
            onLoadUpdated(false)
        }
    }
}