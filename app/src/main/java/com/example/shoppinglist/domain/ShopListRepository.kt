package com.example.shoppinglist.domain

import kotlinx.coroutines.flow.Flow

interface ShopListRepository {

    fun getShopList() : Flow<List<ShopItem>>
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(shopItem: ShopItem)
    suspend fun editShopItem(shopItem: ShopItem)
    suspend fun getShopItem(itemId : Int) : ShopItem?
}