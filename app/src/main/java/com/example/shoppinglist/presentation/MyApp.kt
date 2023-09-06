package com.example.shoppinglist.presentation

import android.app.Application
import com.example.shoppinglist.di.AppComponent
import com.example.shoppinglist.di.DaggerAppComponent

class MyApp : Application() {

    var component: AppComponent = DaggerAppComponent.factory().create(this)
}