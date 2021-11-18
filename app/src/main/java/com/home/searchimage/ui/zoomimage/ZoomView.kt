package com.home.searchimage.ui.zoomimage

import android.os.Bundle
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ZoomView: MvpView {
    fun setZoomImage()
    fun setDefaultImage()
    fun downloadClick()
}