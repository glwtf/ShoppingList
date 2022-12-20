package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val shopItemListDao = AppDataBase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    /*override fun getShopList() :  LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>()
        .apply {
            addSource(shopItemListDao.getShopList()) {
                value = mapper.mapListDbModelToListEntity(it)
            }
        }*/
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