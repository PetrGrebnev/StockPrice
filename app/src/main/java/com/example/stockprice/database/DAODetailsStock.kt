package com.example.stockprice.database

import androidx.room.*
import com.example.stockprice.models.database.DetailsModelDatabase

@Dao
interface DAODetailsStock {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetailsStock(details: DetailsModelDatabase)

    @Update
    fun updateDetailsStock(details: DetailsModelDatabase)

    @Query("SELECT * FROM details_stock_table WHERE symbol=:symbol")
    fun getStock(symbol: String): DetailsModelDatabase


}