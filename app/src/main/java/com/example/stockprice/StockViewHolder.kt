package com.example.stockprice

import androidx.recyclerview.widget.RecyclerView
import com.example.stockprice.databinding.ListItemStockBinding
import com.example.stockprice.models.database.StockModelDatabase

class StockViewHolder(
    private val binding: ListItemStockBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StockModelDatabase) {
        binding.symbolStock.text = item.symbol
        binding.fullNameStock.text = item.nameStock
    }
}