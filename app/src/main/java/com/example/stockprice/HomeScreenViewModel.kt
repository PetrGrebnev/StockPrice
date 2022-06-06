package com.example.stockprice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenViewModel(private val repository: Repository): ViewModel() {

    private val _stokcs: MutableLiveData<List<StockModel>>()
    val stocks: LiveData<List<StockModel>> = _stokcs

    fun loadAllStocks(){
        repository.getAllStock(object : Callback<ListStockModelApi>{
            override fun onResponse(
                call: Call<ListStockModelApi>,
                response: Response<ListStockModelApi>
            ) {
                val responseBody = response.body()

            }

            override fun onFailure(call: Call<ListStockModelApi>, t: Throwable) {
            }

        })
    }

}