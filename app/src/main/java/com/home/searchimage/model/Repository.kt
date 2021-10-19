package com.home.searchimage.model

import com.home.searchimage.model.data.ImageMainScreenDataList
import retrofit2.Callback

interface Repository {
    fun getImageListFromServer(
        request: String,
        callback: Callback<ImageMainScreenDataList>
    )
}