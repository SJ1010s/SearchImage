package com.home.searchimage.di.modules

import com.home.searchimage.MainActivity
import com.home.searchimage.di.ImageSearchSubcomponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import java.util.*

@Module(subcomponents = [ImageSearchSubcomponent::class])
abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindAndroidInjectorFactory(factory: ImageSearchSubcomponent.Factory): AndroidInjector.Factory<MainActivity>

}