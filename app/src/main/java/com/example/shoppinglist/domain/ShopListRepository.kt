package com.example.shoppinglist.domain

interface ShopListRepository {

    fun getShopList() : List<ShopItem>
    fun addShopItem(item: ShopItem)
    fun deleteShopItem(item: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItem(itemId : Int) : ShopItem?
}