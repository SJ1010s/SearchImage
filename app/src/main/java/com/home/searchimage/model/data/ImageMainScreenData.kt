package com.home.searchimage.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.Url

@Parcelize
data class ImageMainScreenDataList(val hits: List<ImageMainScreenData>?) : Parcelable


@Parcelize
data class ImageMainScreenData(
    val id: Int?,
    val previewURL: String?,
    val largeImageURL: String?,
    val views: Int?,
    val downloads: Int?,
) : Parcelable