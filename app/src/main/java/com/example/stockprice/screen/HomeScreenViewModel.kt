package com.example.stockprice.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stockprice.Repository
import com.example.stockprice.ResultState
import com.example.stockprice.modelapi.ListStockApiModel
import com.example.stockprice.modelapi.StockModelApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class HomeScreenViewModel(private val repository: Repository) : ViewModel() {

    private var _stokcs = MutableLiveData<ResultState<List<StockModelApi>>>()
    val stocks: MutableLiveData<ResultState<List<StockModelApi>>> = _stokcs


    init {
        loadAllStocks()
    }


    fun loadAllStocks() {
//        val list: List<StockModel> = listOf(StockModel(
//            "aaaa",
//            "A",
//            false,
//            false,
//            "sssss",
//        ))
//        _stokcs.value = list
        _stokcs.value = ResultState.Loading()

        repository.getAllStock(object : Callback<ListStockApiModel> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<ListStockApiModel>,
                response: Response<ListStockApiModel>
            ) {
                val result = response.body()?.let { responseBody ->
                    val stocklList = responseBody.data.map {
                        StockModelApi(
                            name = it.name,
                            symbol = it.symbol,
                            country = it.country,
                            currency = it.currency,
                            exchange = it.exchange,
                            mic_code = it.mic_code,
                            type = it.type
                        )
                    }
                    ResultState.Success(stocklList)
                } ?: ResultState.Error(RuntimeException("Response Body null"))
                _stokcs.value = result
                Log.d("Main", "HEllo $result")
            }

            override fun onFailure(call: Call<ListStockApiModel>, t: Throwable) {
                _stokcs.value = ResultState.Error(t)
            }

        })
    }

}