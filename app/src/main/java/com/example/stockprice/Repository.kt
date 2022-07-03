package com.example.stockprice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stockprice.application.Mappers
import com.example.stockprice.application.ResultState
import com.example.stockprice.database.DAODetailsStock
import com.example.stockprice.database.DAOListStocks
import com.example.stockprice.models.api.AvatarModelApi
import com.example.stockprice.models.api.DetailsStockModelApi
import com.example.stockprice.models.api.ListStockModelApi
import com.example.stockprice.models.api.StockModelApi
import com.example.stockprice.models.database.DetailsModelDatabase
import com.example.stockprice.models.database.StockModelDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import kotlin.RuntimeException

class Repository(
    private val stockApi: StockApi,
    private val daoListStocks: DAOListStocks,
    private val daoDetailsStock: DAODetailsStock,
    private val ioExecutor: Executor,
    private val mappers: Mappers
) {

    fun getAllStockASC(): List<StockModelDatabase> = daoListStocks.getAllStocksASC()

    fun getAllStockDESC(): List<StockModelDatabase> = daoListStocks.getAllStockDESC()

    fun searchStock(queryStock: String?) = daoListStocks.searchStock(queryStock)

    fun getListStockApi(callback: Callback<ListStockModelApi>) {
        stockApi.getAllStocks().enqueue(callback)
    }

    fun insertStocks(listStock: List<StockModelDatabase>){
        daoListStocks.addAllListStock(listStock)
    }

    fun updateStocks(listStock: List<StockModelDatabase>){
        daoListStocks.updateStocks(listStock)
    }

    fun getAvatarStock(symbol: String, callback: Callback<AvatarModelApi>) {
        stockApi.getAvatar(symbol).enqueue(callback)
    }

    fun getDetails(symbol: String, callback: Callback<DetailsStockModelApi>) {
        stockApi.getDetails(symbol).enqueue(callback)
    }

    fun insertDetailsStock(stock: DetailsModelDatabase){
        daoDetailsStock.addDetailsStock(stock)
    }

    fun updateDetailsStock(stock: DetailsModelDatabase){
        daoDetailsStock.updateDetailsStock(stock)
    }

    fun getDetailsStock(symbol: String) = daoDetailsStock.getStock(symbol)
}