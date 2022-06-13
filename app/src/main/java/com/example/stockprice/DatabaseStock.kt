package com.example.stockprice

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StockModelDatabase::class], version = 1, exportSchema = false)
abstract class DatabaseStock(): RoomDatabase() {
    abstract val stockDatabaseDAO: DAO
//
//    companion object{
//        private var INSTANCE: DatabaseStock? = null
//        fun getInstance(context: Context): DatabaseStock {
//            synchronized(this) {
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        DatabaseStock::class.java,
//                        "stock_history_database"
//                    )
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//
//        }
//    }
}