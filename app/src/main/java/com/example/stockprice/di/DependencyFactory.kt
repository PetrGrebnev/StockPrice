package com.example.stockprice.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.stockprice.application.PermissionChecker
import com.example.stockprice.database.DAODetailsStock
import com.example.stockprice.database.DAOListStocks
import com.example.stockprice.database.DatabaseStock
import com.example.stockprice.model.Repository
import com.example.stockprice.model.StockApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyFactory {

    fun createApi(retrofit: Retrofit) = retrofit.create(StockApi::class.java)

    fun createRetrofit(logger: HttpLoggingInterceptor) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(StockApi.BASE_URL)
        .client(OkHttpClient.Builder().apply {
            addInterceptor(logger)
        }
            .build())
        .build()

    fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor {
            Log.d("OkHHTPClient", it)
        }
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