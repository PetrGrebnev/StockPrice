package com.example.stockprice

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.stockprice.models.database.StockModelDatabase

class DataSource(
    private val repository: Repository
): PagingSource<Int, StockModelDatabase>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, StockModelDatabase> {
        try {
// Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 0
            val loadSize = params.loadSize
            val response = repository.getAll(loadSize, nextPageNumber)
            return LoadResult.Page(
                data = response as ArrayList<StockModelDatabase>,
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StockModelDatabase>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
// either the prevKey or the nextKey, but you need to handle nullability
        // here:
        // * prevKey == null -> anchorPage is the first page.
        // * nextKey == null -> anchorPage is the last page.
        // * both prevKey and nextKey null -> anchorPage is the initial page, so
// just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}