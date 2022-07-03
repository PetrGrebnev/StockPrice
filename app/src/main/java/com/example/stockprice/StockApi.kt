package com.example.stockprice


import com.example.stockprice.models.api.AvatarModelApi
import com.example.stockprice.models.api.ListStockModelApi
import com.example.stockprice.models.api.DetailsStockModelApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("stocks?exchange=NASDAQ")
    fun getAllStocks(): Call<ListStockModelApi>

    @GET("logo?$ACCESS_KEY")
    fun getAvatar(@Query("symbol") sym: String): Call<AvatarModelApi>

    @GET("eod?$ACCESS_KEY")
    fun getDetails(@Query("symbol") sym: String): Call<DetailsStockModelApi>

    companion object {
        private const val ACCESS_KEY = "apikey=b6aa7009088e4398924c175e7186d9be"
        var BASE_URL = "https://api.twelvedata.com/"
    }
}