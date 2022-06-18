package com.example.stockprice.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockprice.Repository
import com.example.stockprice.application.MyUtils
import com.example.stockprice.di.DependencyStorage
import kotlinx.coroutines.launch

class HomeSearchViewModel(private val repository: Repository): ViewModel(){

    init {
        load()
    }

    private fun load(){
        if (MyUtils.isInternetAvailable(DependencyStorage.Android.applicationContext)) {
            viewModelScope.launch {
                repository.getListStockApi()
            }
        }
    }
}
