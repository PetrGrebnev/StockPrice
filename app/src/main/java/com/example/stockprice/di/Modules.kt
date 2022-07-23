package com.example.stockprice.di

import org.koin.dsl.module

val stockApiModule = module {
    single { DependencyFactory.createLoggingInterceptor() }
    single { DependencyFactory.createRetrofit(get()) }
    single { DependencyFactory.createApi(get()) }
}

val databaseModule = module {
    single { DependencyFactory.createDatabase(get()) }
    single { DependencyFactory.createDaoList(get()) }
    single { DependencyFactory.createDaoDetails(get()) }
}

val mainModule = module {
    single { DependencyFactory.createRepository(get(), get(), get())}
}

val appModules = listOf(
    stockApiModule,
    databaseModule,
    mainModule
)