package com.example.stockprice.di

import com.example.stockprice.Mappers
import org.koin.dsl.module

val stockApiModule = module {
    single { DependencyFactory.createLoggingInterceptor() }
    single { DependencyFactory.createHttpClient(get()) }
    single { DependencyFactory.createRetrofit(get()) }
    single { DependencyFactory.createApi(get()) }
    single { DependencyFactory.createRepository(get(),get()) }
}

val databaseModule = module {
    single { DependencyFactory.createDatabase(get())}
    single { DependencyFactory.createDao(get()) }
    single { Mappers() } //***
}

val appModules = listOf(stockApiModule, databaseModule)