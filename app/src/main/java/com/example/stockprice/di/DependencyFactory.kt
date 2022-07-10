package com.example.stockprice.di

import android.content.Context
import androidx.databinding.ktx.BuildConfig
import androidx.room.Room
import com.example.stockprice.*
import com.example.stockprice.application.PermissionChecker
import com.example.stockprice.application.Mappers
import com.example.stockprice.database.DAODetailsStock
import com.example.stockprice.database.DAOListStocks
import com.example.stockprice.database.DatabaseStock
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
        daoListStocks: DAOListStocks,
        daoDetailsStock: DAODetailsStock,
    ) = Repository(stockApi, daoListStocks, daoDetailsStock)


    fun createPermissionChecker(applicationContext: Context): PermissionChecker {
        return PermissionChecker(applicationContext)
    }

    fun createDaoList(
        databaseStock: DatabaseStock
    ): DAOListStocks {
        return databaseStock.listStocksDAO
    }

    fun createDaoDetails(databaseStock: DatabaseStock): DAODetailsStock {
        return databaseStock.detailsStockDAO
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
}