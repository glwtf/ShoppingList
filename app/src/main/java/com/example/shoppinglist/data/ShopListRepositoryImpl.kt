package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {
    private val liveShopList = MutableLiveData<List<ShopItem>>()
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    init {
        for (i in 0 until 10){
            val item = ShopItem("name $i", i, true)
            addShopItem(item)
        }
    }

    override fun getShopList() = liveShopList

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = autoIncrementId++
        }
        shopList.add(item)
        updateList()
    }

    override fun deleteShopItem(item: ShopItem) {
        shopList.remove(item)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        getShopItem(shopItem.id)?.let { deleteShopItem(it) }
        addShopItem(shopItem)
    }

    override fun getShopItem(itemId: Int) = shopList.find{ item -> item.id == itemId }

    private fun updateList(){
        liveShopList.value = shopList.toList()
    }

}