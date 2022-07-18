package com.example.stockprice.application

import com.example.stockprice.models.api.DetailsStockModelApi
import com.example.stockprice.models.database.StockModelDatabase
import com.example.stockprice.models.api.ListStockModelApi
import com.example.stockprice.models.api.StockModelApi
import com.example.stockprice.models.database.DetailsModelDatabase

class Mappers {

    fun stockModelDatabase(stock: StockModelApi, id: Int) = StockModelDatabase(
        id = id,
        country = stock.country,
        exchange = stock.exchange,
        mic_code = stock.mic_code,
        nameStock = stock.name,
        symbol = stock.symbol,
    )

    fun detailsModelsData(stock: DetailsStockModelApi, avatar: String?) = DetailsModelDatabase(
        closePrice = stock.close,
        currency = stock.currency,
        exchange = stock.exchange,
        mic_code = stock.mic_code,
        symbol = stock.symbol,
        datetime = stock.datetime,
        avatar = avatar
    )

    fun listStockModelData(list: ListStockModelApi, id:Int) = list.data.map {
        stockModelDatabase(it, id)
    }
}