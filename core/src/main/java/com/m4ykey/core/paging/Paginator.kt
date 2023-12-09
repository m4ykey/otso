package com.m4ykey.core.paging

class Paginator<Key, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private val getNextKey: suspend (List<Item>) -> Key,
    private val onError: suspend (Throwable?) -> Unit,
    private val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
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

        try {
            val result = onRequest(currentKey)

            result.onSuccess { items ->
                if (items.isNotEmpty()) {
                    currentKey = getNextKey(items)
                    onSuccess(items, currentKey)
                } else {
                    onLoadUpdated(false)
                }
            }

            result.onFailure {
                onError(it)
            }
        } finally {
            isMakingRequest = false
            onLoadUpdated(false)
        }
    }
}
