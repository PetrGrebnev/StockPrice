package com.example.stockprice

import com.example.stockprice.modelapi.ListStockApiModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface StockApi {

    @GET("stocks?$ACCESS_KEY")
    fun getAllStocks(): Call<ListStockApiModel>

    companion object {
        private const val ACCESS_KEY = "apikey="

        var BASE_URL = "https://api.twelvedata.com/"

        fun create(): StockApi {
            val httpClient = OkHttpClient.Builder()

            if (BuildConfig.DEBUG){
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                httpClient.addInterceptor(logging)
            }

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .build()

            return retrofit.create(StockApi::class.java)
        }
    }
}