package com.example.stockprice.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_stock_table")
data class StockModelDatabase(
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "exchange") val exchange: String,
    @ColumnInfo(name = "mic_code") val mic_code: String,
    @ColumnInfo(name = "name") val nameStock: String,
    @PrimaryKey
    @ColumnInfo(name = "symbol") val symbol: String,
)