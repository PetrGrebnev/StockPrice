package com.example.stockprice.screen

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stockprice.Mappers
import com.example.stockprice.Repository
import com.example.stockprice.ResultState
import com.example.stockprice.modelapi.ListStockApiModel
import com.example.stockprice.modelapi.StockModelApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class AllLIstStockViewModel(
    private val repository: Repository,
    private val mappers: Mappers
) : ViewModel() {

    private var _stokcs = MutableLiveData<ResultState<List<StockModelApi>>>()
    val stocks: MutableLiveData<ResultState<List<StockModelApi>>> = _stokcs


    init {
        loadAllStocks()
    }


    fun loadAllStocks() {
        _stokcs.value = ResultState.Loading()

        repository.getAllStock(object : Callback<ListStockApiModel> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<ListStockApiModel>,
                response: Response<ListStockApiModel>
            ) {
                val responseBody = response.body()

                if (responseBody == null){
                   _stokcs.value = ResultState.Error(RuntimeException("Response Body null"))
                } else {
                    val stock = mappers.listStockModelApi(responseBody)
                    _stokcs.value = ResultState.Success(stock)
                    val stockData = mappers.listStockModelData(responseBody)
                    repository.addStockDatabase(stockData)
                }
//                val result = response.body()?.let { responseBody ->
//                    val stocklList = responseBody.data.map {
//                        StockModelApi(
//                            name = it.name,
//                            symbol = it.symbol,
//                            country = it.country,
//                            currency = it.currency,
//                            exchange = it.exchange,
//                            mic_code = it.mic_code,
//                            type = it.type
//                        )
//                    }
//                    ResultState.Success(stocklList)
//                } ?: ResultState.Error(RuntimeException("Response Body null"))
//                _stokcs.value = result
//                Log.d("Main", "HEllo $result")
            }

            override fun onFailure(call: Call<ListStockApiModel>, t: Throwable) {
                _stokcs.value = ResultState.Error(t)
            }

        })
    }

}