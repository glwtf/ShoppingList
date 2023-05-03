package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopItemListDao: ShopItemListDao,
    private val mapper : ShopListMapper,
) : ShopListRepository {

    override fun getShopList() :  LiveData<List<ShopItem>>
    = Transformations.map(shopItemListDao.getShopList()) {
        mapper.mapListDbModelToListEntity(it)
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        val item = mapper.mapEntityToDbModel(shopItem)
        shopItemListDao.addShopItemList(item)
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopItemListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        val item = mapper.mapEntityToDbModel(shopItem)
        shopItemListDao.addShopItemList(item)
    }

    override suspend fun getShopItem(itemId: Int) : ShopItem {
        val item = shopItemListDao.getShopItem(itemId)
        return mapper.mapDbModelToEntity(item)
    }

}