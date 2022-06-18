package com.example.stockprice

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DAO {

    @Insert
    fun addAllListStock(list: List<StockModelDatabase>)

    @Query("SELECT * FROM list_stock_table ")
    fun getAllStocks(): List<StockModelDatabase>
}