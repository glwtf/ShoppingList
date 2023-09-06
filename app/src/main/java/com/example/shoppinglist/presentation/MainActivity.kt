package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.presentation.recyclerview.ShopListAdapter
import com.example.shoppinglist.presentation.viewmodel.MainViewModel
import com.example.shoppinglist.presentation.viewmodel.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.onEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var rvAdapter: ShopListAdapter
    private lateinit var buttonAddItem : FloatingActionButton
    private var shopItemContainer : FragmentContainerView? = null

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as MyApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)

        setContentView(R.layout.activity_main)
        buttonAddItem = findViewById(R.id.button_add_shop_item)
        shopItemContainer = findViewById(R.id.shop_item_container)

        setupRecyclerView()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.liveShopList.collect{ item ->
                    rvAdapter.submitData(item)
                }
            }
        }


    }

    private fun setupRecyclerView(){
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvShopList) {
            rvAdapter = ShopListAdapter()
            adapter = rvAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        //setup implementation of listener
        setupLongClickListener()
        setupClickListener()

        //swipe to delete
        setupSwipeListener(rvShopList)
    }

    private fun setupClickListener() {
        rvAdapter.onShopItemClickListener = { shopItem ->
            if (shopItemContainer == null ) {
                val intent = ShopItemActivity.newIntentEditItem(this, shopItem.id)
                startActivity(intent)
            }
            else {
                launchFragment(ShopItemFragment.newIntentEditItem(shopItem.id))
            }
        }

        buttonAddItem.setOnClickListener {
            if (shopItemContainer == null ) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }
            else {
                launchFragment(ShopItemFragment.newIntentAddItem())
            }
        }
    }

    private fun setupLongClickListener() {
        rvAdapter.onShopItemLongClickListener = { shopItem ->
            viewModel.changeEnableShopItem(shopItem)
        }
    }

    private fun setupSwipeListener(rvShopList : RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean { return false }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = rvAdapter.getItemByPos(viewHolder.adapterPosition)
                item?.let { viewModel.deleteShopItem(item) }
            }
        }).attachToRecyclerView(rvShopList)
    }

    private fun launchFragment(fragment : Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}