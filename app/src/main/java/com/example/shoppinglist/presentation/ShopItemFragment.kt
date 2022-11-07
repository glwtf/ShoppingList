package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(
    private var screenMode : String = UNKNOWN_MODE,
    private var shopItemId : Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var shopItemViewModel : ShopItemViewModel

    private lateinit var tilName : TextInputLayout
    private lateinit var tilCount : TextInputLayout
    private lateinit var etName : EditText
    private lateinit var etCount : EditText
    private lateinit var buttonSave : Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
        setTextChangeListener()
        setErrorListener()
        setCloseListener()
    }

    private fun setTextChangeListener() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) { }

        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) { }

        })
    }

    private fun setErrorListener() {
        shopItemViewModel.errorInputCount.observe(viewLifecycleOwner) { state ->
            if (state == true) {
                tilCount.error = "Bad count"
            }
            else {
                tilCount.error = null
            }
        }
        shopItemViewModel.errorInputName.observe(viewLifecycleOwner) { state ->
            if (state == true) {
                tilName.error = "Bad name"
            }
            else {
                tilName.error = null
            }
        }
    }

    private fun setCloseListener() {
        shopItemViewModel.closeActivity.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun initViews(view: View){
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.save_button)
    }

    private fun parseParams() {
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD) {
            throw java.lang.RuntimeException("Param screen mode is absent")
        }
        if (screenMode != MODE_EDIT && shopItemId != ShopItem.UNDEFINED_ID) {
            throw java.lang.RuntimeException("Param shop item id is absent")
        }
    }

    private fun launchEditMode() {
        shopItemViewModel.getShopItem(shopItemId)
        shopItemViewModel.editItem.observe(viewLifecycleOwner) { item ->
            etName.setText(item.name)
            etCount.setText(item.count.toString())
        }

        buttonSave.setOnClickListener {
            shopItemViewModel.editShopItem(
                etName.text?.toString(),
                etCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            shopItemViewModel.addShopItem(
                etName.text?.toString(),
                etCount.text?.toString())
        }
    }

    companion object {

        private const val UNKNOWN_MODE = ""
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

        fun newIntentAddItem() = ShopItemFragment(MODE_ADD)

        fun newIntentEditItem(itemId : Int) = ShopItemFragment(MODE_EDIT, itemId)
    }

}