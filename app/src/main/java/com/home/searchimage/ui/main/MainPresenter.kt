package com.home.searchimage.ui.main

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter() : MvpPresenter<MainView>() {

    private val itemViewClickListener get() = ItemViewClickListener(this)
    private val images = mutableListOf<GithubUser>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    class ItemViewClickListener(val presenter: MainPresenter) : OnItemViewClickListener {
        override fun onItemViewClick(userID: String) {
            presenter.itemClick(userID)
        }
    }
}