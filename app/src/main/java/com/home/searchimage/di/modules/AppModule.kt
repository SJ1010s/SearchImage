package com.home.searchimage.di.modules

import com.home.searchimage.ImageSearch
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: ImageSearch) {

    @Provides
    fun app(): ImageSearch {
        return app
    }

}
