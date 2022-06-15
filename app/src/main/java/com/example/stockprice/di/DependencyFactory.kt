package com.example.stockprice.di

import android.content.Context
import androidx.databinding.ktx.BuildConfig
import androidx.room.Room
import com.example.stockprice.DAO
import com.example.stockprice.DatabaseStock
import com.example.stockprice.application.PermissionChecker
import com.example.stockprice.Repository
import com.example.stockprice.StockApi
import com.example.stockprice.application.Mappers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object DependencyFactory {

    fun createApi(retrofit: Retrofit) = retrofit.create(StockApi::class.java)

    fun createRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(StockApi.BASE_URL)
        .client(client)
        .build()

    fun createHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(logger)
        }
        return httpClient.build()
    }

    fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    fun createRepository(
        stockApi: StockApi,
        dao: DAO,
        ioExecutor: Executor,
        applicationContext: Context,
        mappers: Mappers
    ) = Repository(stockApi, dao, ioExecutor, applicationContext, mappers)


    fun createPermissionChecker(applicationContext: Context): PermissionChecker {
        return PermissionChecker(applicationContext)
    }

    fun createDao(databaseStock: DatabaseStock): DAO {
        return databaseStock.stockDatabaseDAO
    }

    fun createDatabase(applicationContext: Context): DatabaseStock {
        return Room.databaseBuilder(
            applicationContext,
            DatabaseStock::class.java,
            "stock_history_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun createIoExecutor(): Executor {
        return Executors.newFixedThreadPool(4)
    }
}