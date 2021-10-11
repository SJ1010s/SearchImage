package com.home.searchimage

import android.app.Application
import com.home.searchimage.di.DaggerImageSearchComponent
import com.home.searchimage.di.ImageSearchSubcomponent
import com.home.searchimage.di.modules.AppModule
import com.home.searchimage.di.modules.CiceroneModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ImageSearch: Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    companion object {
        lateinit var instance: ImageSearch
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

}