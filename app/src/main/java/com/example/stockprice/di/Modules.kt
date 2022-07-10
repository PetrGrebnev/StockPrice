package com.example.stockprice.di

import com.example.stockprice.application.Mappers
import org.koin.dsl.module

val stockApiModule = module {
    single { DependencyFactory.createLoggingInterceptor() }
    single { DependencyFactory.createHttpClient(get()) }
    single { DependencyFactory.createRetrofit(get()) }
    single { DependencyFactory.createApi(get()) }
}

val databaseModule = module {
    single { DependencyFactory.createDatabase(get()) }
    single { DependencyFactory.createDaoList(get()) }
    single { DependencyFactory.createDaoDetails(get()) }
}

val mainModule = module {
    single { Mappers() }
    single { DependencyFactory.createRepository(get(), get(), get())}
}

val appModules = listOf(
    stockApiModule,
    databaseModule,
    mainModule
)