package com.example.stockprice

import com.example.stockprice.modelapi.ListStockApiModel
import retrofit2.Callback

class Repository(private val stockApi: StockApi) {

    fun getAllStock(callback: Callback<ListStockApiModel>) {
        stockApi.getAllStocks().enqueue(callback)
    }

}