package com.example.stockprice.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "details_stock_table")
data class DetailsModelDatabase(
    @ColumnInfo(name = "close_price")val closePrice: Double?,
    @ColumnInfo(name = "currency")val currency: String?,
    @ColumnInfo(name = "datetime")val datetime: String?,
    @ColumnInfo(name = "exchange")val exchange: String?,
    @ColumnInfo(name = "mic_code")val mic_code: String?,
    @PrimaryKey
    @ColumnInfo(name = "symbol")val symbol: String,
    @ColumnInfo(name = "avatar")val avatar: String?
)
