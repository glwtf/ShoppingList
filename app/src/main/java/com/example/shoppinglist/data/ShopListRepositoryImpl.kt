package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {
    private val shoplist = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    override fun getShopList() = shoplist.toList()

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = autoIncrementId++
        }
        shoplist.add(item)
    }

    override fun deleteShopItem(item: ShopItem) {
        shoplist.remove(item)
    }

    override fun editShopItem(shopItem: ShopItem) {
        getShopItem(shopItem.id)?.let { deleteShopItem(it) }
        addShopItem(shopItem)
    }

    override fun getShopItem(itemId: Int) = shoplist.find{ item -> item.id == itemId }

}