package com.home.searchimage

import android.app.Application
import com.home.searchimage.di.DaggerImageSearchAppComponent
import com.home.searchimage.di.ImageSearchAppComponent
import com.home.searchimage.di.modules.CiceroneModule

class ImageSearch: Application() {

    private lateinit var component: ImageSearchAppComponent

    companion object {
        lateinit var instance: ImageSearch
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerImageSearchAppComponent
            .builder()
            .ciceroneModule(CiceroneModule())
            .build()
        getComponent().inject(this)
    }

    fun getComponent():ImageSearchAppComponent{
        return component
    }

}