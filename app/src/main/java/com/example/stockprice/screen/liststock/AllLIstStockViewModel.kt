package com.example.stockprice.screen.liststock

import android.widget.Toast
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.stockprice.DataSource
import com.example.stockprice.Repository
import com.example.stockprice.SortOrder
import com.example.stockprice.application.Mappers
import com.example.stockprice.application.ResultState
import com.example.stockprice.models.database.StockModelDatabase
import com.example.stockprice.application.MyUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.getKoin
import kotlin.RuntimeException

class AllLIstStockViewModel(
    private val repository: Repository,
    private val mappers: Mappers
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

    val flow = Pager(
        PagingConfig(20)
    ) {
        DataSource(getKoin().get())
    }.flow
        .cachedIn(viewModelScope)

    private fun internetConnection() =
        MyUtils.isInternetAvailable(KoinJavaComponent.getKoin().get())

    fun loadingFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            _stocks.postValue(ResultState.Loading())
            if (internetConnection()) {
                val response = repository.getListStockApi()
                if (response.body() == null) {
                    _stocks.value = ResultState.Error(RuntimeException("Error"))
                    return@launch
                } else {
                    val stockData = mappers.listStockModelData(response.body()!!)
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