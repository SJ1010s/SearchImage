package com.home.searchimage

import android.app.Application
import com.home.searchimage.di.DaggerImageSearchAppComponent
import com.home.searchimage.di.ImageSearchAppComponent
import com.home.searchimage.di.modules.CiceroneModule

class ImageSearch: Application() {

    companion object {
        lateinit var instance: ImageSearch
        private lateinit var component: ImageSearchAppComponent

        fun getComponent():ImageSearchAppComponent{
            return component
        }
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



}