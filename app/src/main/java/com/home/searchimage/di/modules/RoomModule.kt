package com.home.searchimage.di.modules

import androidx.room.Room
import com.home.searchimage.ImageSearch
import com.home.searchimage.model.room.AppDataBase
import com.home.searchimage.model.room.SearchRequestDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    private var db: AppDataBase? = null

    @Provides
    @Singleton
    fun getDB(): SearchRequestDao {
        if (db == null) {
            synchronized(AppDataBase::class.java) {
                if (db == null) {
                    db = Room.databaseBuilder(
                        ImageSearch.instance.applicationContext,
                        AppDataBase::class.java,
                        "search_image_db.db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
        }

        return db!!.searchRequestDao()
    }
}