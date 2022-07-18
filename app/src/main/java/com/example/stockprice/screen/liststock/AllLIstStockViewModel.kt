package com.example.stockprice.screen.liststock

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.stockprice.Repository
import com.example.stockprice.StockListItem
import com.example.stockprice.application.Mappers
import com.example.stockprice.application.ResultState
import com.example.stockprice.models.database.StockModelDatabase
import com.example.stockprice.application.MyUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.RuntimeException
import androidx.paging.*



class AllLIstStockViewModel(
    private val repository: Repository,
    private val mappers: Mappers,
) : ViewModel() {

    private val _stocks = MutableLiveData<ResultState<List<StockModelDatabase>>>()
    val stocks: LiveData<ResultState<List<StockModelDatabase>>> = _stocks
//
//    private val _sortOrder = MutableLiveData<SortOrder>()
//    val sortOrder: LiveData<SortOrder> = _sortOrder
//
//    var oldSortOrder = SortOrder.ASC

    init {
        loadingFromApi()
    }

    val data = Pager(
        PagingConfig(
            pageSize = 25,
            enablePlaceholders = false,
            initialLoadSize = 50
        ),
    ) {
        repository.getAll()
    }.flow.cachedIn(viewModelScope)

//    val allStocks: Flow<PagingData<StockListItem>> = Pager(
//        config = PagingConfig(
//            pageSize = 2,
//            enablePlaceholders = true,
//            maxSize = 6
//        )
//    ) {
//        repository.getAll()
//    }.flow
//        .map { pagingData ->
//            pagingData
//                .map { stock -> StockListItem.Item(stock) }
//                .insertSeparators { before: StockListItem?, after: StockListItem? ->
//                    if (before == null && after == null) {
//                        // List is empty after fully loaded; return null to skip adding separator.
//                        null
//                    } else if (after == null) {
//                        // Footer; return null here to skip adding a footer.
//                        null
//                    } else if (before == null) {
//                        // Header
//                        StockListItem.Separator(after.symbol.first())
//                    } else if (!before.symbol.first().equals(after.symbol.first(), ignoreCase = true)) {
//                        // Between two items that start with different letters.
//                        StockListItem.Separator(after.symbol.first())
//                    } else {
//                        // Between two items that start with the same letter.
//                        null
//                    }
//                }
//        }
//        .cachedIn(viewModelScope)

    private fun internetConnection() =
        MyUtils.isInternetAvailable(KoinJavaComponent.getKoin().get())

    fun loadingFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            _stocks.postValue(ResultState.Loading())
            var id = 0
            if (internetConnection()) {
                val response = repository.getListStockApi()
                if (response.body() == null) {
                    _stocks.value = ResultState.Error(RuntimeException("Error"))
                    return@launch
                } else {
                    id++
                    val stockData = mappers.listStockModelData(response.body()!!, id)
                    _stocks.postValue(ResultState.Success(stockData))
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.insertStocks(stockData)
                        repository.updateStocks(stockData)
                    }
                }
            } else {
                Toast.makeText(
                    KoinJavaComponent.getKoin().get(),
                    "Not connection internet",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    var query: String? = null

//    fun listAllStock(sortOrder: SortOrder) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _stocks.postValue(ResultState.Loading())
//            when (sortOrder) {
//                SortOrder.ASC -> {
//                    _stocks.postValue(ResultState.Success(repository.getAllStockASC()))
//                }
//                SortOrder.DESC -> {
//                    _stocks.postValue(ResultState.Success(repository.getAllStockDESC()))
//                }
//                SortOrder.SEARCH -> {
//                    _stocks.postValue(ResultState.Success(repository.searchStock(query)))
//                }
//            }
//        }
//    }
//
//    fun setSortOrder(sortOrder: SortOrder) {
//        if (sortOrder != SortOrder.SEARCH) {
//            oldSortOrder = sortOrder
//        }
//        _sortOrder.value = sortOrder
//    }
}