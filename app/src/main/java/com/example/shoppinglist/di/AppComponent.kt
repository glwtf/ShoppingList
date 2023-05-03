package com.example.shoppinglist.di

import android.app.Application
import com.example.shoppinglist.presentation.MainActivity
import com.example.shoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface ComponentFactory {
        fun create(
            @BindsInstance application: Application
        ) : AppComponent
    }
}