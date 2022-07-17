package com.example.stockprice

import com.example.stockprice.models.database.StockModelDatabase

sealed class StockListItem(val symbol: String) {
    data class Item (
        val stock: StockModelDatabase
        ) : StockListItem(stock.symbol)
    data class Separator(
        private val letter: Char
    ) : StockListItem(letter.toUpperCase().toString())
}