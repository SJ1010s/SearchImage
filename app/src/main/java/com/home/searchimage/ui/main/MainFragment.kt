package com.home.searchimage.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.home.searchimage.R
import com.home.searchimage.databinding.MainFragmentBinding
import com.home.searchimage.model.data.ImageMainScreenData
import com.home.searchimage.ui.main.adapter.MainRVAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class MainFragment : MvpAppCompatFragment(), MainView {

    private var _viewBinding: MainFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val presenter: MainPresenter by moxyPresenter { MainPresenter() }
    private var adapter: MainRVAdapter? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = MainFragmentBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initRV(){
        viewBinding.rvImages.layoutManager = LinearLayoutManager(context)
        adapter = MainRVAdapter(presenter, presenter.itemViewClickListener)
        viewBinding.rvImages.adapter = adapter
    }

    override fun setImages(images: List<ImageMainScreenData>) {
        adapter?.setImageList(images)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
