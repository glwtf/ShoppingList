package com.example.shoppinglist.domain

class DeleteShopItemUseCase(private val repository: ShopListRepository) {

    fun deleteShopItem(item: ShopItem){
        repository.deleteShopItem(item)
    }
}