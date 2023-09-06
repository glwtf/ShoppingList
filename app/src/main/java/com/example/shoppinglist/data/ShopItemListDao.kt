package com.example.shoppinglist.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopItemListDao {

    @Query("SELECT * FROM shop_items")
    fun getShopList(): PagingSource<Int, ShopItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItemList(shopItemDbModel: ShopItemDbModel)

    @Query("DELETE FROM shop_items WHERE id=:shopItemId")
    suspend fun deleteShopItem(shopItemId: Int)

    @Query("SELECT * FROM shop_items WHERE id=:shopItemId LIMIT 1")
    suspend fun getShopItem(shopItemId: Int) : ShopItemDbModel
}