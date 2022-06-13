package com.example.stockprice.application

import android.app.Application
import com.example.stockprice.di.DependencyStorage
import com.example.stockprice.di.appModules
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        DependencyStorage.init(this)

        startKoin {
            androidContext(this@MyApp)
            modules(appModules)
        }
    }
}