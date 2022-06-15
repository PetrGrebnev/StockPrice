package com.example.stockprice.di

import android.content.Context
import com.example.stockprice.DAO
import com.example.stockprice.DatabaseStock
import com.example.stockprice.application.Mappers
import com.example.stockprice.application.PermissionChecker
import com.example.stockprice.Repository

object DependencyStorage {
    fun init(applicationContext: Context) {
        Android.init(applicationContext)
        Database.init(applicationContext)
        Dao.init()
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

    object Dao {
        lateinit var dao: DAO
            private set

        fun init() {
            dao = DependencyFactory.createDao(Database.databaseStock)
        }
    }

    object Repositories {
        lateinit var repository: Repository
            private set

        fun init() {
            repository = DependencyFactory.createRepository(
                Api.stockApi,
                Dao.dao,
                Executor.ioExecutor,
                Android.applicationContext,
                Mapper.mapper
            )
        }
    }

    object Mapper {
        val mapper = Mappers()
    }

    object Executor {
        val ioExecutor = DependencyFactory.createIoExecutor()
    }
}