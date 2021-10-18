package com.home.searchimage.ui.zoomimage

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

class ZoomScreen(private val bundle: Bundle): FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment {
        val zoomFragment = ZoomFragment.newInstance()
        zoomFragment.setArguments(bundle)
        return zoomFragment
    }
}