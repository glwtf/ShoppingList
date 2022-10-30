package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {
    private val liveShopList = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)})
    private var autoIncrementId = 0

    init {
        for (i in 0 until 10000){
            val item = ShopItem("name $i", i, Random.nextBoolean())
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