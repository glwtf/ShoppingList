package com.example.shoppinglist.domain

class EditShopItemUseCase(private val repository: ShopListRepository) {

    suspend fun editShopItem(shopItem: ShopItem){
        repository.editShopItem(shopItem)
    }
}