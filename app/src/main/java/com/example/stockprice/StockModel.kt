package com.example.stockprice

data class StockModel(
    val name: String,
    val symbol: String,
    val hasIntraday: Boolean,
    val hasEod: Boolean,
    val country: String,
    val stock_exchange: StockExchange
)