package com.home.searchimage.ui.zoomimage

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.home.searchimage.R

import com.home.searchimage.databinding.ImageZoomFragmentBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.lang.NullPointerException


class ZoomFragment() : MvpAppCompatFragment(), ZoomView, DownlodFromServer {

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

    override fun setDefaultImage() {
        viewBinding.zoomImageView.setImageResource(R.drawable.ic_baseline_image_100)
    }

    override fun downloadClick() {
        viewBinding.downloadButton.setOnClickListener(View.OnClickListener {
            presenter.imageDownload(this, this)
            Toast(requireContext()).apply {
                setText("Изображение скачано")
                show()
            }
        })
    }



    override fun setZoomImage() {
        val zoomableImageView = viewBinding.zoomImageView
        Glide
            .with(this)
            .asBitmap()
            .load(getURL())
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    zoomableImageView.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    override fun getURL(): String {
        val bundle = getArguments()
        val URLFromBundle = bundle?.getString("key")
        if (URLFromBundle != null)
            return URLFromBundle
        else {
            throw NullPointerException()
        }
    }

}
