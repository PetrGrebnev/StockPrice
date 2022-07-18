//package com.example.stockprice
//
//import android.util.Log
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.stockprice.models.database.StockModelDatabase
//import kotlinx.coroutines.delay
//
//class DataSourceStock(
//    private val repository: Repository,
//) : PagingSource<Int, StockModelDatabase>() {
//
//    override suspend fun load(
//        params: LoadParams<Int>,
//    ): LoadResult<Int, StockModelDatabase> {
//
//        Log.d("PPPP", "DS")
//        return try {
//            val page = params.key ?: 0
//
//            val response = repository.getAll(params.loadSize, page * params.loadSize)
//
////            if (page != 0) delay(1000)
//
//            LoadResult.Page(
//                data = response,
//                prevKey = if (page == 0) null else page - 1,
//                nextKey = if (response.isEmpty()) null else page + 1
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, StockModelDatabase>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//}