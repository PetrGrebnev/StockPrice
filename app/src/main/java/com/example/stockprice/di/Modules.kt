package com.example.stockprice.di

import org.koin.dsl.module

val stockApiModule = module{
    single { DependencyFactory.createLoggingInterceptor() }
    single { DependencyFactory.createHttpClient(get()) }
    single { DependencyFactory.createRetrofit(get()) }
    single { DependencyFactory.createApi(get()) }
    single { DependencyFactory.createRepository(get()) }
}

val appModules = listOf(stockApiModule)