package com.example.shoppinglist.domain

class GetShopItemUseCase(private val repository: ShopListRepository) {

    fun getShopItem(itemId : Int) = repository.getShopItem(itemId)
}