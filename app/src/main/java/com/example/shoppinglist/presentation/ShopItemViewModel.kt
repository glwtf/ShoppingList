package com.example.shoppinglist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(
                name = name,
                count = count,
                enabled = true
            )
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun getShopItem(itemId: Int){
        getShopItemUseCase.getShopItem(itemId)
    }

    fun editShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(
                name = name,
                count = count,
                enabled = true
            )
            editShopItemUseCase.editShopItem(shopItem)
        }
    }

    private fun parseName(inputName : String?) = inputName?.trim() ?: ""

    private fun parseCount(inputCount: String?) = inputCount?.trim()?.toIntOrNull() ?: 0

    private fun validateInput(name : String, count : Int) : Boolean {
        return when {
            name.isBlank() -> {
                Log.d("Gleb", "show error input name")
                false
            }
            count <= 0 -> {
                Log.d("Gleb", "show error input count")
                false
            }
            else -> true
        }
    }
}