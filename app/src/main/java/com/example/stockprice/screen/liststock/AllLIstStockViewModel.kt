package com.example.stockprice.screen.liststock

import android.widget.Toast
import androidx.lifecycle.*
import com.example.stockprice.Repository
import com.example.stockprice.SortOrder
import com.example.stockprice.application.Mappers
import com.example.stockprice.application.ResultState
import com.example.stockprice.models.database.StockModelDatabase
import com.example.stockprice.application.MyUtils
import com.example.stockprice.models.api.ListStockModelApi
import org.koin.java.KoinJavaComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import kotlin.RuntimeException

class AllLIstStockViewModel(
    private val repository: Repository,
    private val ioExecutor: Executor,
    private val mappers: Mappers
) : ViewModel() {

    private val _stocks = MutableLiveData<ResultState<List<StockModelDatabase>>>()
    val stocks: LiveData<ResultState<List<StockModelDatabase>>> = _stocks

    private val _sortOrder = MutableLiveData<SortOrder>()
    val sortOrder: LiveData<SortOrder> = _sortOrder

    var oldSortOrder = SortOrder.ASC

    init {
        loadingFromApi()
    }

    private fun internetConnection() =
        MyUtils.isInternetAvailable(KoinJavaComponent.getKoin().get())

    fun loadingFromApi() {
        _stocks.postValue(ResultState.Loading())
        if (internetConnection()) {
            repository.getListStockApi(object : Callback<ListStockModelApi> {

                override fun onResponse(
                    call: Call<ListStockModelApi>,
                    response: Response<ListStockModelApi>
                ) {
                    val responseBody = response.body()
                    if (responseBody == null) {
                        _stocks.value = ResultState.Error(RuntimeException("Error"))
                        return
                    } else {
                        val stockData = mappers.listStockModelData(responseBody)
                        _stocks.value = ResultState.Success(stockData)
                        ioExecutor.execute {
                            repository.insertStocks(stockData)
                            repository.updateStocks(stockData)
                        }
                    }
                }

                override fun onFailure(call: Call<ListStockModelApi>, t: Throwable) {
                    Toast.makeText(
                        KoinJavaComponent.getKoin().get(),
                        "Error $t",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
        } else {
            Toast.makeText(
                KoinJavaComponent.getKoin().get(),
                "Not connection internet",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    var query: String? = null

    fun listAllStock(sortOrder: SortOrder) {
        _stocks.value = ResultState.Loading()
        when (sortOrder) {
            SortOrder.ASC -> {
                ioExecutor.execute {
                    _stocks.postValue(ResultState.Success(repository.getAllStockASC()))
                }
            }
            SortOrder.DESC -> {
                ioExecutor.execute {
                    _stocks.postValue(ResultState.Success(repository.getAllStockDESC()))
                }
            }
            SortOrder.SEARCH -> {
                ioExecutor.execute {
                    _stocks.postValue(ResultState.Success(repository.searchStock(query)))
                }
            }
        }
    }

    fun setSortOrder(sortOrder: SortOrder){
        if (sortOrder != SortOrder.SEARCH){
            oldSortOrder = sortOrder
        }
        _sortOrder.value = sortOrder
    }
}