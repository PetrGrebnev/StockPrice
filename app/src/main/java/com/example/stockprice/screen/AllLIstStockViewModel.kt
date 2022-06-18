package com.example.stockprice.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockprice.application.Mappers
import com.example.stockprice.Repository
import com.example.stockprice.ResultState
import com.example.stockprice.StockModelDatabase
import com.example.stockprice.application.MyUtils
import com.example.stockprice.di.DependencyStorage
import kotlinx.coroutines.launch

class AllLIstStockViewModel(
    private val repository: Repository,
    private val mappers: Mappers
) : ViewModel() {

//    private var _stokcs = ResultState<List<StockModelDatabase>>()
//    val stocks: ResultState<List<StockModelDatabase>> = _stokcs

//    init {
//        if (MyUtils.isInternetAvailable(DependencyStorage.Android.applicationContext)) {
//            viewModelScope.launch {
//                repository.getListStockApi()
//            }
//        }
//    }

    fun loadListStock(): LiveData<ResultState<List<StockModelDatabase>>> =
        repository.getAllStock()

//    private fun loadAllStocks() {
//        _stokcs.value = ResultState.Loading()
//
//        _stokcs.value = ResultState.Success(repository.getAllStock())
////        _stokcs.value = ResultState.Loading()
////
////        repository.getAllStock(object : Callback<ListStockApiModel> {
////            @SuppressLint("NullSafeMutableLiveData")
////            override fun onResponse(
////                call: Call<ListStockApiModel>,
////                response: Response<ListStockApiModel>
////            ) {
////                val responseBody = response.body()
////
////                if (responseBody == null){
////                   _stokcs.value = ResultState.Error(RuntimeException("Response Body null"))
////                } else {
////                    val stock = mappers.listStockModelApi(responseBody)
////                    _stokcs.value = ResultState.Success(stock)
////                    val stockData = mappers.listStockModelData(responseBody)
////                    repository.addStockDatabase(stockData)
////                }
////            }
////            override fun onFailure(call: Call<ListStockApiModel>, t: Throwable) {
////                _stokcs.value = ResultState.Error(t)
////            }
////        })
//    }

}