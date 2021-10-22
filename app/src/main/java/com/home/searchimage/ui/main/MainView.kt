package com.home.searchimage.ui.main

import com.home.searchimage.model.Repository
import com.home.searchimage.model.data.ImageMainScreenData
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface MainView: MvpView {
    fun initRV()
    fun setImages(images: List<ImageMainScreenData>)
    fun getInputSearchTextListener()
}