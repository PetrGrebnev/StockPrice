package com.example.stockprice.models.api

data class StockModelApi(
    val country: String,
    val currency: String,
    val exchange: String,
    val mic_code: String,
    val name: String,
    val symbol: String,
    val type: String
)