package com.example.stockprice.model

import com.example.stockprice.database.DAODetailsStock
import com.example.stockprice.database.DAOListStocks
import com.example.stockprice.datamodels.database.DetailsModelDatabase
import com.example.stockprice.datamodels.database.StockModelDatabase

class Repository(
    private val stockApi: StockApi,
    private val daoListStocks: DAOListStocks,
    private val daoDetailsStock: DAODetailsStock,
) {

    suspend fun getAllStockASC(): List<StockModelDatabase> = daoListStocks.getAllStocksASC()

    suspend fun getAllStockDESC(): List<StockModelDatabase> = daoListStocks.getAllStockDESC()

    suspend fun searchStock(queryStock: String?) = daoListStocks.searchStock(queryStock)

    suspend fun getListStockApi() = stockApi.getAllStocks()

    suspend fun insertStocks(listStock: List<StockModelDatabase>) {
        daoListStocks.addAllListStock(listStock)
    }

    suspend fun getAvatarStock(symbol: String) = stockApi.getAvatar(symbol)

    suspend fun getDetails(symbol: String) = stockApi.getDetails(symbol)

    suspend fun insertDetailsStock(stock: DetailsModelDatabase) {
        daoDetailsStock.addDetailsStock(stock)
    }

    suspend fun updateDetailsStock(stock: DetailsModelDatabase) {
        daoDetailsStock.updateDetailsStock(stock)
    }

    suspend fun getDetailsStock(symbol: String) = daoDetailsStock.getStock(symbol)
}