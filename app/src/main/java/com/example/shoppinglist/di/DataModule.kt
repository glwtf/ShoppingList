package com.example.shoppinglist.di

import android.app.Application
import com.example.shoppinglist.data.AppDataBase
import com.example.shoppinglist.data.ShopItemListDao
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule.BindsDataModule::class])
class DataModule {

    @Provides
    fun providesShopItemListDao(application: Application) : ShopItemListDao {
        return AppDataBase.getInstance(application).shopListDao()
    }

    @Module
    interface BindsDataModule {
        @Binds
        abstract fun bindsShopListRepository(impl: ShopListRepositoryImpl) : ShopListRepository
    }
}