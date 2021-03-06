package com.home.searchimage.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

object MainScreen: FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment {
       return MainFragment.newInstance()
    }
}