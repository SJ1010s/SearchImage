package com.home.searchimage.ui.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import com.github.terrakok.cicerone.Router
import com.home.searchimage.ImageSearch
import com.home.searchimage.model.Repository
import com.home.searchimage.model.RepositoryImpl
import com.home.searchimage.model.data.ImageMainScreenData
import com.home.searchimage.model.data.ImageMainScreenDataList
import com.home.searchimage.model.data.RemoteDataSource
import com.home.searchimage.model.room.SearchRequestDao
import com.home.searchimage.model.room.SearchRequestTable
import com.home.searchimage.ui.zoomimage.ZoomScreen
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@InjectViewState
class MainPresenter() : MvpPresenter<MainView>() {

    val itemViewClickListener = ItemViewClickListener(this)
    private val images = mutableListOf<ImageMainScreenData>()
    var repository: Repository? = null
    private val disposables = CompositeDisposable()

    //    private lateinit var component: ImageSearchActivityComponent
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var getDB: SearchRequestDao

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
        images.clear()
        imagesData.hits?.forEach {
            if (it.downloads != null &&
                it.previewURL != null &&
                it.largeImageURL != null &&
                it.id != null &&
                it.views != null
            ) {
                images.add(it)
                Log.d(TAG, "значение запроса: ${it.previewURL}")
            }else{
                Log.d(TAG, "checkResponse: в массиве значение = null")
            }
        }
        viewState.setImages(images)
    }

    override fun onFirstViewAttach() {
        repository = RepositoryImpl(RemoteDataSource())
        super.onFirstViewAttach()

        ImageSearch.getComponent().inject(this)

        viewState.initRV()
        initImageList()
        viewState.getInputSearchTextListener()
        viewState.updateTextView()
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
        repository?.getImageListFromServer(request, callback)
        viewState.setImages(images)
    }

    fun setSingleTextToDB(textFromEditText: String) {
        disposables.add(
            getDB.getItem(item = textFromEditText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ itemText ->
                    println("onSuccess")
                },
                    { println("onError: ${it.message}") },
                    { setTextToDB(textFromEditText)
                        println("onComplete")})
        )
    }

    fun setTextToDB(textFromEditText: String) {
        disposables.add(
            getDB
                .retain(SearchRequestTable(request = textFromEditText))
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d(TAG, "setTextToDB: success")
                },
                    { Log.d(TAG, "setTextToDB: error") })
        )
    }


    fun getAllFromDB(): List<String> {
        val getAll: MutableList<String> = mutableListOf()
        disposables.add(getDB.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { get ->
                getAll.clear()
                getAll.addAll(get)
            }
        )
        return getAll
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

}