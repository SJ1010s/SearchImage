package com.home.searchimage.ui.zoomimage

import com.github.terrakok.cicerone.ResultListener
import com.github.terrakok.cicerone.Router
import com.home.searchimage.ImageSearch
import moxy.MvpPresenter
import javax.inject.Inject

class ZoomPresenter() : MvpPresenter<ZoomView>() {

//    private lateinit var component: ImageSearchActivityComponent

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        ImageSearch.getComponent().inject(this)
        viewState.setDefaultImage()
        viewState.setZoomImage()
        viewState.downloadClick()
    }
}