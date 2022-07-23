package com.example.stockprice.screen.details

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockprice.model.Repository
import com.example.stockprice.utils.MyUtils
import com.example.stockprice.utils.ResultState
import com.example.stockprice.utils.detailsModelsData
import com.example.stockprice.datamodels.database.DetailsModelDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class DetailsStockViewModel(
    symbolStock: String,
    private val repository: Repository,
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
        if (internetConnection()) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.getAvatarStock(symbolStock)
                if (response.isSuccessful) {
                    avatarStock = response.body()!!.url
                }
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

    private fun getStockDetails(symbolStock: String) {
        _stock.postValue(ResultState.Loading())
        if (internetConnection()) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.getDetails(symbolStock)
                if (response.body()?.code != 200) {
                    response.body()?.let {
                        val stock = detailsModelsData(
                            it, avatarStock, symbolStock
                        )
                        _stock.postValue(ResultState.Success(stock))
                        repository.insertDetailsStock(stock)
                        repository.updateDetailsStock(stock)
                    }
                } else {
                    _stock.postValue(ResultState.Error(RuntimeException("Error")))
                }
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
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
