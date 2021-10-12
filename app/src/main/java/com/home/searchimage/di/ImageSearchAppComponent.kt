package com.home.searchimage.di

import com.home.searchimage.ImageSearch
import com.home.searchimage.MainActivity
import com.home.searchimage.di.modules.AppModule
import com.home.searchimage.di.modules.CiceroneModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CiceroneModule::class])
interface ImageSearchAppComponent {
    fun inject(app: ImageSearch)
}