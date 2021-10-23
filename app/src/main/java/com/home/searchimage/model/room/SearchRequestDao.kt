package com.home.searchimage.model.room

import androidx.room.*

@Dao
interface SearchRequestDao {
    @Query("SELECT * FROM search_request")
    fun getAll(): List<SearchRequestTable>

    @Query("SELECT request FROM search_request WHERE request=:item LIMIT 1")
    fun getItem(item: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun retain(addItem: SearchRequestTable)

    @Delete
    fun delete(deleteItem: SearchRequestTable)
}