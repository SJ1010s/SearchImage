package com.home.searchimage.model

import com.home.searchimage.BuildConfig
import com.home.searchimage.model.data.ImageMainScreenDataList
import com.home.searchimage.model.data.RemoteDataSource
import retrofit2.Callback

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : Repository {
    override fun getImageListFromServer(
        request: String,
        callback: Callback<ImageMainScreenDataList>
    ) {
        return remoteDataSource.getImageData(
            BuildConfig.PIXABAY_API_KEY,
            request,
            callback
        )
    }
}