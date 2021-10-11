package com.home.searchimage.di

import com.home.searchimage.ImageSearch
import com.home.searchimage.di.modules.AppModule
import com.home.searchimage.di.modules.CiceroneModule
import com.home.searchimage.di.modules.MainActivityModule
import dagger.Component

@Component(modules = [AppModule::class, CiceroneModule::class, MainActivityModule::class])
interface ImageSearchComponent {
    fun inject(imageSearch: ImageSearch)
}