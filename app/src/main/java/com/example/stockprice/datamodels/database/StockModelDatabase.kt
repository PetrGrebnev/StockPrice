package com.example.stockprice.datamodels.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_stock_table")
data class StockModelDatabase(
    @ColumnInfo(name = "name") val nameStock: String,
    @PrimaryKey
    @ColumnInfo(name = "symbol") val symbol: String
)