package com.example.stockprice.models.api

data class DetailsStockModelApi(
    val close: Double,
    val currency: String,
    val datetime: String,
    val exchange: String,
    val mic_code: String,
    val symbol: String,
    val timestamp: Int
)