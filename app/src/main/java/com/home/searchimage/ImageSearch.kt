package com.home.searchimage

import android.app.Application
import androidx.room.Room
import com.home.searchimage.di.DaggerImageSearchAppComponent
import com.home.searchimage.di.ImageSearchAppComponent
import com.home.searchimage.di.modules.CiceroneModule
import com.home.searchimage.model.room.AppDataBase
import com.home.searchimage.model.room.SearchRequestDao

class ImageSearch: Application() {

    companion object {
        lateinit var instance: ImageSearch
        private lateinit var component: ImageSearchAppComponent

        fun getComponent():ImageSearchAppComponent{
            return component
        }

        private var db: AppDataBase? = null
        fun getDB(): SearchRequestDao {
            if (db == null) {
                synchronized(AppDataBase::class.java) {
                    if (db == null) {
                        db = Room.databaseBuilder(
                            instance.applicationContext,
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