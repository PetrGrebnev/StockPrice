package com.example.stockprice.utils

import com.example.stockprice.datamodels.api.DetailsStockModelApi
import com.example.stockprice.datamodels.database.StockModelDatabase
import com.example.stockprice.datamodels.api.StockModelApi
import com.example.stockprice.datamodels.database.DetailsModelDatabase

fun stockModelDatabase(stock: StockModelApi) = StockModelDatabase(
    nameStock = stock.name,
    symbol = stock.symbol,
)

fun detailsModelsData(stock: DetailsStockModelApi, avatar: String?, symbols: String) = DetailsModelDatabase(
    closePrice = stock.close,
    currency = stock.currency,
    exchange = stock.exchange,
    mic_code = stock.mic_code,
    symbol = stock.symbol ?: symbols,
    datetime = stock.datetime,
    avatar = avatar
)
