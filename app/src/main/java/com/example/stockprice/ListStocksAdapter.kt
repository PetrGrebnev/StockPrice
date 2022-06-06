package com.example.stockprice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListStocksAdapter(
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<ListStocksAdapter.ViewHolder>(){

    private var stocks: MutableList<Stock> = mutableListOf()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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
            itemFullName.text = item.fullName
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}