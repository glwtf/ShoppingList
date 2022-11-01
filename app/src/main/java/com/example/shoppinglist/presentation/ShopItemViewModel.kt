package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName : LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount : LiveData<Boolean>
        get() = _errorInputCount

    private val _editItem = MutableLiveData<ShopItem>()
    val editItem : LiveData<ShopItem>
        get() = _editItem

    private val _closeActivity = MutableLiveData<Unit>()
    val closeActivity : LiveData<Unit>
        get() = _closeActivity

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
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _editItem.value?.let { item ->
                val tmpItem = item.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(tmpItem)
                finishWork()
            }
        }
    }

    fun getShopItem(itemId: Int) {
        val item = getShopItemUseCase.getShopItem(itemId)
        _editItem.value = item
    }

    private fun parseName(inputName : String?) = inputName?.trim() ?: ""

    private fun parseCount(inputCount: String?) = inputCount?.trim()?.toIntOrNull() ?: 0

    private fun validateInput(name : String, count : Int) : Boolean {
        var ret = true
        if (name.isBlank()) {
            _errorInputName.value = true
            ret = false
        }
        if (count < 1) {
            _errorInputCount.value = true
            ret = false
        }
        return ret
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _closeActivity.value = Unit
    }
}