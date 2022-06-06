package com.example.stockprice

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface StockApi {

    @GET("tickers?$ACCESS_KEY")
    fun getAllStocks(): Call<ListStockModelApi>

    companion object {
        private const val ACCESS_KEY = "access_key="

        var BASE_URL = "https://reqres.in/api/"

        fun create(): StockApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(StockApi::class.java)
        }
    }
}