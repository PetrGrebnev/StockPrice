package com.example.stockprice

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.stockprice.databinding.ListItemStockBinding
import com.example.stockprice.models.database.StockModelDatabase

class StockPagingAdapter(
//    private val onClick: (symbol: String) -> Unit
) : PagingDataAdapter<StockModelDatabase, StockViewHolder>(StockDiffUtil()) {

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        getItem(position)?.let { item ->
            Log.d("PPPP", "Adapter ${item.symbol}}")
            holder.bind(item)
//            holder.itemView.setOnClickListener{
//                onClick(item.symbol)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            ListItemStockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}