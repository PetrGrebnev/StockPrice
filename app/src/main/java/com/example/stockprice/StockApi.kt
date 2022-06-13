package com.example.stockprice


import com.example.stockprice.modelapi.ListStockApiModel
import retrofit2.Call
import retrofit2.http.GET

interface StockApi {

    @GET("stocks")
    fun getAllStocks(): Call<ListStockApiModel>

    companion object {
        private const val ACCESS_KEY = "apikey="
        var BASE_URL = "https://api.twelvedata.com/"
    }
}