package com.example.stockprice

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface DAO {


    @Insert
    fun addAllListStock(list: List<StockModelDatabase>)

}