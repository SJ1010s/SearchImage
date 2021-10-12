package com.home.searchimage

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.home.searchimage.di.DaggerImageSearchActivityComponent
import com.home.searchimage.di.ImageSearchActivityComponent
import com.home.searchimage.di.ImageSearchAppComponent
import com.home.searchimage.di.modules.CiceroneModule
import com.home.searchimage.ui.main.MainScreen
import moxy.MvpAppCompatActivity
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity() {

    private val navigator = AppNavigator(this, android.R.id.content)

    private lateinit var component: ImageSearchActivityComponent

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        component = DaggerImageSearchActivityComponent
            .builder()
            .ciceroneModule(CiceroneModule())
            .build()
        getComponent().inject(this)
        savedInstanceState ?: router.newRootScreen(MainScreen)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }
    }

    fun getComponent(): ImageSearchActivityComponent{
        return component
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}