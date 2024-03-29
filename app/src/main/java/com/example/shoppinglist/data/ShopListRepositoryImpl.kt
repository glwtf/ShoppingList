package com.example.shoppinglist.data

import androidx.paging.PagingSource
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopItemListDao: ShopItemListDao,
    private val mapper : ShopListMapper,
) : ShopListRepository {

    override fun getShopList(): PagingSource<Int, ShopItem> {
        return shopItemListDao.getShopList()
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