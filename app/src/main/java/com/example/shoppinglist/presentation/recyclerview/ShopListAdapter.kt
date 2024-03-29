package com.example.shoppinglist.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : PagingDataAdapter<
        ShopItem,
        ShopItemViewHolder
        >(ShopItemDiffCallBack())
{
    var onShopItemLongClickListener : ((ShopItem) -> Unit)? = null
    var onShopItemClickListener : ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder { //create view
        val layout = when(viewType){
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw java.lang.RuntimeException("Unknown ViewType: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) { //set data in view
        val shopItem = getItem(position)
        shopItem?.let {
            holder.tvName.text = shopItem.name
            holder.tvCount.text = shopItem.count.toString()
            holder.view.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }
            holder.view.setOnClickListener {
                onShopItemClickListener?.invoke(shopItem)
            }
        }
    }

    fun getItemByPos(position: Int) = getItem(position)

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item?.enabled == true) VIEW_TYPE_ENABLED
        else VIEW_TYPE_DISABLED
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 2
        const val MAX_POOL_SIZE = 15
    }
}