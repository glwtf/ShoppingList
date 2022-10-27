package com.example.shoppinglist.domain

class GetShopListUseCase(private val repository: ShopListRepository) {

    fun getShopList() = repository.getShopList()
}