package com.example.shoppinglist.domain

import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(private val repository: ShopListRepository) {

    suspend fun getShopItem(itemId : Int) = repository.getShopItem(itemId)
}