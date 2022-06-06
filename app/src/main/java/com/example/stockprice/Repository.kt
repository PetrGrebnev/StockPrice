package com.example.stockprice

import retrofit2.Callback

class Repository(private val stockApi: StockApi) {

    fun getAllStock(callback: Callback<ListStockModelApi>) {
        stockApi.getAllStocks().enqueue(callback)
    }

}