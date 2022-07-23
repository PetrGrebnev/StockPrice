package com.example.stockprice.screen.liststock

import android.widget.Toast
import androidx.lifecycle.*
import com.example.stockprice.model.Repository
import com.example.stockprice.utils.SortOrder
import com.example.stockprice.utils.ResultState
import com.example.stockprice.datamodels.database.StockModelDatabase
import com.example.stockprice.utils.MyUtils
import com.example.stockprice.utils.stockModelDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class AllLIstStockViewModel(
    private val repository: Repository,
) : ViewModel() {

    private val _stocks = MutableLiveData<ResultState<List<StockModelDatabase>>>()
    val stocks: LiveData<ResultState<List<StockModelDatabase>>> = _stocks

    private var _sortOrder = MutableLiveData<SortOrder>()
    val sortOrder: LiveData<SortOrder> = _sortOrder

    var oldSortOrder = SortOrder.ASC
    var query: String? = null

    init {
        loadingFromApi()
        listAllStock(SortOrder.ASC)
    }

    fun setSortOrder(newSortOrder: SortOrder) {
        if (newSortOrder != SortOrder.SEARCH) {
            oldSortOrder = newSortOrder
            this._sortOrder.value = oldSortOrder
        }
        this._sortOrder.value = newSortOrder
    }

    private fun internetConnection() =
        MyUtils.isInternetAvailable(KoinJavaComponent.getKoin().get())

    private fun loadingFromApi() {
        _stocks.value = ResultState.Loading()
        if (internetConnection()) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.getListStockApi()
                if (response.body() != null) {
                    repository.insertStocks(response.body()!!.data.map {
                        stockModelDatabase(it)
                    })
                    _stocks.postValue(ResultState.Success(repository.getAllStockASC()))
                    return@launch
                }
            }
        } else {
            _stocks.value = ResultState.Error(RuntimeException("Not internet connection"))
            Toast.makeText(
                KoinJavaComponent.getKoin().get(),
                "Not connection internet",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    fun listAllStock(sortOrder: SortOrder) {
        viewModelScope.launch(Dispatchers.IO) {
            _stocks.postValue(ResultState.Loading())
            when (sortOrder) {
                SortOrder.ASC -> {
                    _stocks.postValue(ResultState.Success(repository.getAllStockASC()))
                }
                SortOrder.DESC -> {
                    _stocks.postValue(ResultState.Success(repository.getAllStockDESC()))
                }
                SortOrder.SEARCH -> {
                    _stocks.postValue(ResultState.Success(repository.searchStock(query)))
                }
            }
        }
    }
}