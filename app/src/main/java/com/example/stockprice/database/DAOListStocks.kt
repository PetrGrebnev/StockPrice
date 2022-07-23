package com.example.stockprice.database

import androidx.room.*
import com.example.stockprice.datamodels.database.StockModelDatabase

@Dao
interface DAOListStocks {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllListStock(list: List<StockModelDatabase>)

    @Update
    fun updateStocks(list: List<StockModelDatabase>)

    @Query("SELECT * FROM list_stock_table LIMIT :nextPageNumber, :loadSize")
    fun getAll(loadSize: Int, nextPageNumber: Int): List<StockModelDatabase>

    @Query("SELECT * FROM list_stock_table ORDER BY symbol ASC")
    fun getAllStocksASC(): List<StockModelDatabase>

    @Query ("SELECT * FROM list_stock_table ORDER BY symbol DESC")
    fun getAllStockDESC(): List<StockModelDatabase>

    @Query("SELECT * FROM list_stock_table WHERE symbol LIKE '%' || :queryStock || '%' OR symbol LIKE '%' || :queryStock || '%'")
    fun searchStock(queryStock: String?): List<StockModelDatabase>
}