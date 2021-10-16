package com.home.searchimage.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import com.home.searchimage.model.Repository
import com.home.searchimage.model.RepositoryImpl
import com.home.searchimage.model.data.ImageMainScreenData
import com.home.searchimage.model.data.ImageMainScreenDataList
import com.home.searchimage.model.data.RemoteDataSource
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter() : MvpPresenter<MainView>() {

    val itemViewClickListener = ItemViewClickListener(this)
    private val images = mutableListOf<ImageMainScreenData>()
    var repository: Repository? = null

    private val callback: Callback<ImageMainScreenDataList> =
        object : Callback<ImageMainScreenDataList> {
            override fun onResponse(
                call: Call<ImageMainScreenDataList>,
                response: Response<ImageMainScreenDataList>
            ) {
                val imagesData: ImageMainScreenDataList? = response.body()
                if (response.isSuccessful && imagesData != null) {
                    checkResponse(imagesData)
                } else {
                    throw Throwable("server error")
                }
            }

            override fun onFailure(call: Call<ImageMainScreenDataList>, t: Throwable) {
                throw Throwable("ошибка запроса на сервер $t")
            }
        }

    private fun checkResponse(imagesData: ImageMainScreenDataList) {
        imagesData.hits?.forEach{
            if (it.downloads != null &&
                it.previewURL != null &&
                it.largeImageURL != null &&
                it.id != null &&
                it.views != null
            ) {
                images.add(it)
                Log.d(TAG, "checkResponse: ${it.previewURL}")
            }
        }
        viewState.setImages(images)
    }

    override fun onFirstViewAttach() {
        repository = RepositoryImpl(RemoteDataSource())
        super.onFirstViewAttach()
        viewState.initRV()
        initImageList()
        viewState.getInputSearchTextListener()
    }

    class ItemViewClickListener(val presenter: MainPresenter) : OnItemViewClickListener {
        override fun onItemViewClick(imageLargeURL: String?) {
            presenter.itemClick(imageLargeURL)
        }
    }

    fun itemClick(imageLargeURL: String?) {

    }

    fun getCount(): Int {
        return images.size
    }

    fun initImageList() {
        images.clear()
        repository?.getImageListFromServer("", callback)
    }

    fun getImagesFromSearchText(request: String){
        images.clear()
        repository?.getImageListFromServer(request, callback)
    }
}