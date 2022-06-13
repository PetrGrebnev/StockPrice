package com.example.stockprice

import com.example.stockprice.modelapi.ListStockApiModel
import com.example.stockprice.modelapi.StockModelApi

class Mappers{

    fun stockModelDatabase(stock: StockModelApi) = StockModelDatabase(
        country = stock.country,
        currency = stock.currency,
        exchange = stock.exchange,
        mic_code = stock.mic_code,
        name = stock.name,
        symbol = stock.symbol,
        type = stock.type
    )

    fun stockModelApi(stock: StockModelApi) = StockModelApi(
        country = stock.country,
        currency = stock.currency,
        exchange = stock.exchange,
        mic_code = stock.mic_code,
        name = stock.name,
        symbol = stock.symbol,
        type = stock.type
    )

    fun listStockModelApi(list: ListStockApiModel) = list.data.map {
        stockModelApi(it)
    }

    fun listStockModelData(list: ListStockApiModel) = list.data.map {
        stockModelDatabase(it)
    }
}