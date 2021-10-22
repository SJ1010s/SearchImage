package com.home.searchimage.model.room

import androidx.room.*

@Dao
interface SearchRequestDao {
    @Query("SELECT * FROM search_request")
    fun getAll(): List<SearchRequestTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun retain(addItem: SearchRequestTable)

    @Delete
    fun delete(deleteItem: SearchRequestTable)
}