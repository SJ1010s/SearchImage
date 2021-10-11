package com.home.searchimage.di

import android.content.Context
import com.home.searchimage.ImageSearch
import com.home.searchimage.MainActivity
import com.home.searchimage.di.modules.AppModule
import com.home.searchimage.di.modules.CiceroneModule
import com.home.searchimage.di.modules.MainActivityModule
import com.home.searchimage.ui.main.MainPresenter
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector

import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [MainActivityModule::class])

interface ImageSearchSubcomponent: AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    interface Factory: AndroidInjector.Factory<MainActivity>{

    }
}