package com.example.stockprice


import com.example.stockprice.models.api.AvatarModelApi
import com.example.stockprice.models.api.ListStockModelApi
import com.example.stockprice.models.api.DetailsStockModelApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("stocks")
    suspend fun getAllStocks(): Response<ListStockModelApi>

    @GET("logo?$ACCESS_KEY")
    suspend fun getAvatar(@Query("symbol") sym: String): Response<AvatarModelApi>

    @GET("eod?$ACCESS_KEY")
    suspend fun getDetails(@Query("symbol") sym: String): Response<DetailsStockModelApi>

    companion object {
        private const val ACCESS_KEY = "apikey=b6aa7009088e4398924c175e7186d9be"
        var BASE_URL = "https://api.twelvedata.com/"
    }
}