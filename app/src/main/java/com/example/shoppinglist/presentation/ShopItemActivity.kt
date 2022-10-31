package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R

class ShopItemActivity : AppCompatActivity() {

    private lateinit var shopItemViewModel : ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

    }
}