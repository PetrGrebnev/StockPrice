package com.example.stockprice.screen.details

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockprice.Repository
import com.example.stockprice.application.Mappers
import com.example.stockprice.application.MyUtils
import com.example.stockprice.application.ResultState
import com.example.stockprice.models.api.AvatarModelApi
import com.example.stockprice.models.api.DetailsStockModelApi
import com.example.stockprice.models.database.DetailsModelDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class DetailsStockViewModel(
    symbolStock: String,
    private val repository: Repository,
    private val mappers: Mappers
) : ViewModel() {

    private val _stock = MutableLiveData<ResultState<DetailsModelDatabase>>()
    val stock: LiveData<ResultState<DetailsModelDatabase>> = _stock
    private var avatarStock = ""

    init {
        getAvatarStock(symbolStock)
        getStockDetails(symbolStock)
    }

    private fun internetConnection() =
        MyUtils.isInternetAvailable(KoinJavaComponent.getKoin().get())

    private fun getAvatarStock(symbolStock: String) {
        viewModelScope.launch(Dispatchers.IO) {
            
            if (internetConnection()) {
                val response = repository.getAvatarStock(symbolStock)
                if (response.isSuccessful) {
                    avatarStock = response.body()!!.url
                } else {
                    Toast.makeText(
                        KoinJavaComponent.getKoin().get(),
                        "Error response is body null",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                Toast.makeText(
                    KoinJavaComponent.getKoin().get(),
                    "No internet connection",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun getStockDetails(symbolStock: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _stock.postValue(ResultState.Loading())
            if (internetConnection()) {
                val response = repository.getDetails(symbolStock)
                if (response.isSuccessful) {
                    val stock = mappers.detailsModelsData(
                        response.body()!!, avatarStock
                    )
                    _stock.postValue(ResultState.Success(stock))
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.insertDetailsStock(stock)
                        repository.updateDetailsStock(stock)
                    }
                } else {
                    _stock.postValue(ResultState.Error(RuntimeException("Error")))
                    Toast.makeText(
                        KoinJavaComponent.getKoin().get(),
                        "Error response is body null",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                val stock = repository.getDetailsStock(symbolStock)
                if (stock != null) {
                    _stock.postValue(ResultState.Success(stock))
                } else {
                    _stock.postValue(ResultState.Error(RuntimeException("Is stock null")))
                }
            }
        }
    }
}
