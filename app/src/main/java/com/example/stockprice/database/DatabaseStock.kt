package com.example.stockprice.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stockprice.datamodels.database.DetailsModelDatabase
import com.example.stockprice.datamodels.database.StockModelDatabase

@Database(
    entities = [
        StockModelDatabase::class,
        DetailsModelDatabase::class],
    version = 2,
    exportSchema = false
)
abstract class DatabaseStock : RoomDatabase() {
    abstract val listStocksDAO: DAOListStocks
    abstract val detailsStockDAO: DAODetailsStock
}