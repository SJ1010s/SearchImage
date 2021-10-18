package com.home.searchimage.ui.zoomimage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide

import com.home.searchimage.databinding.ImageZoomFragmentBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.lang.NullPointerException


class ZoomFragment() : MvpAppCompatFragment(), ZoomView {

    private var _viewBinding: ImageZoomFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val presenter: ZoomPresenter by moxyPresenter { ZoomPresenter() }

    companion object Factory {
        fun newInstance(): Fragment {
            return ZoomFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        presenter
        _viewBinding = ImageZoomFragmentBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }


    override fun setZoomImage() {
        Glide
            .with(this)
            .load(getURLFromBundle())
            .into(viewBinding.zoomImageView)
    }

    fun getURLFromBundle(): String {
        val bundle = getArguments()
        val URLFromBundle = bundle?.getString("key")
        if (URLFromBundle!=null)
        return URLFromBundle
        else {
            throw NullPointerException()
        }
    }
}
