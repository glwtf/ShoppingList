package com.example.shoppinglist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.shoppinglist.domain.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getShopListUseCase: GetShopListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
) : ViewModel() {

    val liveShopList = Pager(
        PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
            enablePlaceholders = false
        )
    ) {
        getShopListUseCase.getShopList()
    }.flow.cachedIn(viewModelScope)

    fun deleteShopItem(item: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(item)
        }
    }

    fun changeEnableShopItem(item: ShopItem) {
        viewModelScope.launch {
            val newItem = item.copy(enabled = !item.enabled)
            editShopItemUseCase.editShopItem(newItem)
        }
    }
}