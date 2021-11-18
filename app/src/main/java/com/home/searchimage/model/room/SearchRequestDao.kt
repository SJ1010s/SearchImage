package com.home.searchimage.model.room

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface SearchRequestDao {
    @Query("SELECT request FROM search_request")
    fun getAll(): Observable<List<String>>

    @Query("SELECT request FROM search_request WHERE request=:item LIMIT 1")
    fun getItem(item: String): Maybe<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun retain(addItem: SearchRequestTable): Completable

    @Delete
    fun delete(deleteItem: SearchRequestTable): Completable
}