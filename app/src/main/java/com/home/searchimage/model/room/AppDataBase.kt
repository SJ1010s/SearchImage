package com.home.searchimage.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchRequestTable::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun searchRequestDao(): SearchRequestDao
}