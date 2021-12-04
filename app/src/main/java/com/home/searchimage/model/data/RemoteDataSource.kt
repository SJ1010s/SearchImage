package com.home.searchimage.model.data

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val imageApi = Retrofit.Builder()
        .baseUrl("https://pixabay.com/api/")
        .addConverterFactory(
            GsonConverterFactory
                .create(
                    GsonBuilder()
                        .create()
                )
        )
        .build()
        .create(ImageMainScreenAPI::class.java)

    fun getImageData(
        key: String,
        request: String,
        callback: Callback<ImageMainScreenDataList>
    ) {
        imageApi
            .getImage(
                token = key,
                request = request,
                lang = "ru",
                image_type = "photo",
                per_page = 200
            )
            .enqueue(callback)
    }
}
