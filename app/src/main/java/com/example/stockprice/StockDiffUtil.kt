package com.example.stockprice

import androidx.recyclerview.widget.DiffUtil
import com.example.stockprice.models.database.StockModelDatabase

class StockDiffUtil: DiffUtil.ItemCallback<StockModelDatabase>() {
    override fun areItemsTheSame(
        oldItem: StockModelDatabase,
        newItem: StockModelDatabase
    ): Boolean {
        return oldItem.symbol == newItem.symbol
    }

    override fun areContentsTheSame(
        oldItem: StockModelDatabase,
        newItem: StockModelDatabase
    ): Boolean {
        return oldItem == newItem
    }
}