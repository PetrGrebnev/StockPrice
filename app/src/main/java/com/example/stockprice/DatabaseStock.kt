package com.example.stockprice

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StockModelDatabase::class], version = 1, exportSchema = false)
abstract class DatabaseStock(): RoomDatabase() {
    abstract val stockDatabaseDAO: DAO
}