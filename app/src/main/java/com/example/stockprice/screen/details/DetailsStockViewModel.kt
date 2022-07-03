package com.example.stockprice.screen.details

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stockprice.Repository
import com.example.stockprice.application.Mappers
import com.example.stockprice.application.MyUtils
import com.example.stockprice.application.ResultState
import com.example.stockprice.models.api.AvatarModelApi
import com.example.stockprice.models.api.DetailsStockModelApi
import com.example.stockprice.models.database.DetailsModelDatabase
import org.koin.java.KoinJavaComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class DetailsStockViewModel(
    symbolStock: String,
    private val repository: Repository,
    private val ioExecutor: Executor,
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
        repository.getAvatarStock(symbolStock, object : Callback<AvatarModelApi> {
            override fun onResponse(
                call: Call<AvatarModelApi>,
                response: Response<AvatarModelApi>
            ) {
                val responseBody = response.body()
                if (responseBody == null) {
                    Toast.makeText(
                        KoinJavaComponent.getKoin().get(),
                        "Error response is body null",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    avatarStock = responseBody.url
                }
            }

            override fun onFailure(call: Call<AvatarModelApi>, t: Throwable) {
                Toast.makeText(
                    KoinJavaComponent.getKoin().get(),
                    "Error $t",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }

    private fun getStockDetails(symbolStock: String) {
        _stock.postValue(ResultState.Loading())
        if (internetConnection()) {
            repository.getDetails(symbolStock, object : Callback<DetailsStockModelApi> {
                override fun onResponse(
                    call: Call<DetailsStockModelApi>,
                    response: Response<DetailsStockModelApi>
                ) {
                    val responseBody = response.body()
                    if (responseBody == null) {
                        _stock.value = ResultState.Error(RuntimeException("Error"))
                    } else {
                        if (responseBody.symbol != null) {
                            val stock = mappers.detailsModelsData(responseBody, avatarStock)
                            _stock.value = ResultState.Success(stock)
                            ioExecutor.execute {
                                repository.insertDetailsStock(stock)
                                repository.updateDetailsStock(stock)
                            }
                        } else {
                            Toast.makeText(
                                KoinJavaComponent.getKoin().get(),
                                "Error response is body null",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
                override fun onFailure(call: Call<DetailsStockModelApi>, t: Throwable) {
                    Toast.makeText(
                        KoinJavaComponent.getKoin().get(),
                        "Error $t",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            })
        } else {
            ioExecutor.execute {
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
