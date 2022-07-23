package com.example.stockprice.screen.liststock

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stockprice.R
import com.example.stockprice.datamodels.database.StockModelDatabase

class ListStocksAdapter(
    private val layoutInflater: LayoutInflater,
    private val onClick: (symbol: String, nameStock: String) -> Unit
) : RecyclerView.Adapter<ListStocksAdapter.ViewHolder>(){

    private var stocks: MutableList<StockModelDatabase> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setListNote(stocks: List<StockModelDatabase>) {
        this.stocks = stocks.toMutableList()

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemSymbol: TextView = itemView.findViewById(R.id.symbol_stock)
        val itemFullName: TextView = itemView.findViewById(R.id.full_name_stock)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(
            R.layout.list_item_stock,
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stocks[position]
        holder.apply {
            itemSymbol.text = item.symbol
            itemFullName.text = item.nameStock
        }
        holder.itemView.setOnClickListener{
            onClick(item.symbol, item.nameStock)
        }
    }

    override fun getItemCount(): Int = stocks.size
}