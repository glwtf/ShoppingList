package com.example.shoppinglist.domain

import javax.inject.Inject

class GetShopListUseCase @Inject constructor(private val repository: ShopListRepository) {

    fun getShopList() = repository.getShopList()
}