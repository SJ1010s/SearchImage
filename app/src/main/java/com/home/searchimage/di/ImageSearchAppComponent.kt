package com.home.searchimage.di

import com.home.searchimage.ImageSearch
import com.home.searchimage.MainActivity
import com.home.searchimage.di.modules.AppModule
import com.home.searchimage.di.modules.CiceroneModule
import com.home.searchimage.ui.main.MainPresenter
import com.home.searchimage.ui.zoomimage.ZoomPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, CiceroneModule::class])
interface ImageSearchAppComponent {
    fun inject(app: ImageSearch)
    fun inject(mainActivity: MainActivity)

    fun inject (mainPresenter: MainPresenter)
    fun inject (zoomPresenter: ZoomPresenter)
}