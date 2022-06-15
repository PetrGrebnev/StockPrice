package com.example.stockprice

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.stockprice.application.Mappers
import com.example.stockprice.application.MyUtils
import com.example.stockprice.modelapi.ListStockApiModel
import com.example.stockprice.modelapi.StockModelApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import java.util.concurrent.Executor

class Repository(
    private val stockApi: StockApi,
    private val dao: DAO,
    private val ioExecutor: Executor,
    private val appContext: Context,
    private val mappers: Mappers
) {

    private var stocksApi = MutableLiveData<ResultState<List<StockModelApi>>>()
    private var stocksDatabase: List<StockModelDatabase> = listOf()

    init {
        getAllStock()
    }

    fun getAllStock(): List<StockModelDatabase> {
        if (MyUtils.isInternetAvailable(appContext)) {
            ioExecutor.execute {
                getListStockApi()
            }
        }
        ioExecutor.execute {
            stocksDatabase = dao.getAllStocks()
        }
        return stocksDatabase
    }

    private fun getListStockApi() {
        stockApi.getAllStocks().enqueue(object : Callback<ListStockApiModel> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<ListStockApiModel>,
                response: Response<ListStockApiModel>
            ) {
                val responseBody = response.body()

                if (responseBody == null) {
                    stocksApi.value = ResultState.Error(RuntimeException("Response Body null"))
                } else {
                    val stock = mappers.listStockModelApi(responseBody)
                    stocksApi.value = ResultState.Success(stock)
                    val stockData = mappers.listStockModelData(responseBody)
                    dao.addAllListStock(stockData)
                }
            }

            override fun onFailure(call: Call<ListStockApiModel>, t: Throwable) {
                stocksApi.value = ResultState.Error(t)
            }
        })
    }


}