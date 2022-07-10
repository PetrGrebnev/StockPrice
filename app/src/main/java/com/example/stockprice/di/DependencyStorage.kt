package com.example.stockprice.di

import android.content.Context
import com.example.stockprice.database.DAOListStocks
import com.example.stockprice.database.DatabaseStock
import com.example.stockprice.application.Mappers
import com.example.stockprice.application.PermissionChecker
import com.example.stockprice.Repository
import com.example.stockprice.database.DAODetailsStock

object DependencyStorage {
    fun init(applicationContext: Context) {
        Android.init(applicationContext)
        Database.init(applicationContext)
        DaoListAllStocks.init()
        DaoDetailsStock.init()
        Repositories.init()
    }

    object Android {
        lateinit var applicationContext: Context
            private set

        lateinit var permissionChecker: PermissionChecker
            private set

        fun init(applicationContext: Context) {
            Android.applicationContext = applicationContext
            permissionChecker = DependencyFactory.createPermissionChecker(applicationContext)
        }
    }

    object Network {
        val loggingInterceptor = DependencyFactory.createLoggingInterceptor()
        val httpClient = DependencyFactory.createHttpClient(loggingInterceptor)
        val retrofit = DependencyFactory.createRetrofit(httpClient)
    }

    object Api {
        val stockApi = DependencyFactory.createApi(Network.retrofit)
    }

    object Database {
        lateinit var applicationContext: Context
            private set

        lateinit var databaseStock: DatabaseStock
            private set

        fun init(applicationContext: Context) {
            Database.applicationContext = applicationContext
            databaseStock = DependencyFactory.createDatabase(applicationContext)
        }
    }

    object DaoListAllStocks {
        lateinit var daoListStocks: DAOListStocks
            private set

        fun init() {
            daoListStocks = DependencyFactory.createDaoList(Database.databaseStock)
        }
    }

    object DaoDetailsStock {
        lateinit var daoDetailsStock: DAODetailsStock
            private set

        fun init() {
            daoDetailsStock = DependencyFactory.createDaoDetails(Database.databaseStock)
        }
    }

    object Repositories {
        lateinit var repository: Repository
            private set

        fun init() {
            repository = DependencyFactory.createRepository(
                Api.stockApi,
                DaoListAllStocks.daoListStocks,
                DaoDetailsStock.daoDetailsStock,
            )
        }
    }
}