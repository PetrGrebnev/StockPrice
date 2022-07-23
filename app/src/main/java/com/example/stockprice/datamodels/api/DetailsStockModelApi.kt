package com.example.stockprice.datamodels.api

data class DetailsStockModelApi(
    val code: Int?,
    val message: String?,
    val close: Double,
    val currency: String,
    val datetime: String,
    val exchange: String,
    val mic_code: String,
    val symbol: String,
    val timestamp: Int
)