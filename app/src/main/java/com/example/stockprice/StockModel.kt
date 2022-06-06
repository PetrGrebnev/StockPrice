package com.example.stockprice

class StockModel(
    name: String,
    symbol: String,
    hasIntraday: Boolean,
    hasEod: Boolean,
    country: String,
    stock_exchange: StockExchange
)