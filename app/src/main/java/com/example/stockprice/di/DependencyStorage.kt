package com.example.stockprice.di

import android.content.Context
import com.example.stockprice.application.PermissionChecker
import com.example.stockprice.Repository

object DependencyStorage {
    fun init(applicationContext: Context) {
        Android.init(applicationContext)
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

    object Repositories {
        lateinit var repository: Repository
            private set

        fun init() {
            repository = DependencyFactory.createRepository(Api.stockApi)
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
}