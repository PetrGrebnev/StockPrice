package com.example.stockprice

import com.example.stockprice.modelapi.ListStockApiModel
import retrofit2.Callback
import java.util.concurrent.Executor

class Repository(
    private val stockApi: StockApi,
    private val dao: DAO,
    private val ioExecutor: Executor
) {

    fun getAllStock(callback: Callback<ListStockApiModel>) {
        ioExecutor.execute {
            stockApi.getAllStocks().enqueue(callback)
        }
    }

    fun addStockDatabase(list: List<StockModelDatabase>) {
        ioExecutor.execute {
            dao.addAllListStock(list)
        }
    }

}