package com.example.shoppinglist.domain

import androidx.paging.PagingSource

interface ShopListRepository {

    fun getShopList() : PagingSource<Int, ShopItem>
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(shopItem: ShopItem)
    suspend fun editShopItem(shopItem: ShopItem)
    suspend fun getShopItem(itemId : Int) : ShopItem
}