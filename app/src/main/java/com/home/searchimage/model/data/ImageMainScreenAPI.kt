package com.home.searchimage.model.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ImageMainScreenAPI {
    @GET (".")
    fun getImage(
        @Query("key") token: String,
        @Query("q") request: String,
        @Query("lang") lang: String,
        @Query("image_type") image_type: String,
        @Query("per_page") per_page: Int
    ):Call<ImageMainScreenDataList>
}