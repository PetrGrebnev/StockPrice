package com.example.stockprice

import com.example.stockprice.modelapi.ListStockApiModel
import retrofit2.Callback

class Repository(
    private val stockApi: StockApi,
    private val dao: DAO
) {

    fun getAllStock(callback: Callback<ListStockApiModel>) {
        stockApi.getAllStocks().enqueue(callback)
    }

    suspend fun addStockDatabase(list: List<StockModelDatabase>){
        dao.addAllListStock(list)
    }

}