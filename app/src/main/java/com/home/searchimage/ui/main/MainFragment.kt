package com.home.searchimage.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.home.searchimage.R
import com.home.searchimage.databinding.MainFragmentBinding
import moxy.MvpAppCompatFragment

class MainFragment : MvpAppCompatFragment() {

    private var _viewBinding: MainFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!

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


}
