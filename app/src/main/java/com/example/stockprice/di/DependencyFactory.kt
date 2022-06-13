package com.example.stockprice.di

import android.content.Context
import androidx.databinding.ktx.BuildConfig
import com.example.stockprice.application.PermissionChecker
import com.example.stockprice.Repository
import com.example.stockprice.StockApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyFactory {

    fun createApi(retrofit: Retrofit) = retrofit.create(StockApi::class.java)

    fun createRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(StockApi.BASE_URL)
        .client(client)
        .build()

    fun createHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG){
            httpClient.addInterceptor(logger)
        }
        return httpClient.build()
    }

    fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    fun createRepository(stockApi: StockApi) = Repository(stockApi)

    fun createPermissionChecker(applicationContext: Context): PermissionChecker {
        return PermissionChecker(applicationContext)
    }
}