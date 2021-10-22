package com.home.searchimage.ui.main

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.github.terrakok.cicerone.Router
import com.home.searchimage.ImageSearch
import com.home.searchimage.model.Repository
import com.home.searchimage.model.RepositoryImpl
import com.home.searchimage.model.data.ImageMainScreenData
import com.home.searchimage.model.data.ImageMainScreenDataList
import com.home.searchimage.model.data.RemoteDataSource
import com.home.searchimage.model.room.AppDataBase
import com.home.searchimage.ui.zoomimage.ZoomScreen
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainPresenter() : MvpPresenter<MainView>() {

    val itemViewClickListener = ItemViewClickListener(this)
    private val images = mutableListOf<ImageMainScreenData>()
    var repository: Repository? = null

    //    private lateinit var component: ImageSearchActivityComponent
    @Inject
    lateinit var router: Router

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
        imagesData.hits?.forEach {
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
//        component = DaggerImageSearchActivityComponent
//            .builder()
//            .ciceroneModule(CiceroneModule())
//            .build()
        ImageSearch.getComponent().inject(this)
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
        if (imageLargeURL != null) {
            val bundle = Bundle()
            bundle.putString("key", imageLargeURL)
            router.navigateTo(ZoomScreen(bundle))
        }
    }

    fun getCount(): Int {
        return images.size
    }

    fun initImageList() {
        images.clear()
        repository?.getImageListFromServer("", callback)
    }

    fun getImagesFromSearchText(request: String) {
        images.clear()
        repository?.getImageListFromServer(request, callback)
    }
}