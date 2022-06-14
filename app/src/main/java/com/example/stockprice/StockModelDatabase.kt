package com.example.stockprice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_stock_table")
data class StockModelDatabase(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "exchange") val exchange: String,
    @ColumnInfo(name = "mic_code") val mic_code: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "type") val type: String
)