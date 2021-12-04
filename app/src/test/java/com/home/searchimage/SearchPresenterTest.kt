package com.home.searchimage

import androidx.constraintlayout.motion.utils.ViewState
import com.home.searchimage.model.Repository
import com.home.searchimage.model.RepositoryImpl
import com.home.searchimage.model.data.ImageMainScreenDataList
import com.home.searchimage.ui.main.MainPresenter
import com.home.searchimage.ui.main.MainView
import com.home.searchimage.ui.main.`MainView$$State`
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

import retrofit2.Callback

class SearchPresenterTest {



    @Mock
    private lateinit var mainView: MainView

    @Mock
    private lateinit var mainViewState: `MainView$$State`


    private var repository: Repository? = Mockito.mock(RepositoryImpl::class.java)

    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter()
        presenter.attachView(mainView)
        presenter.setViewState(mainViewState)
        presenter.initImageList()
        presenter.getImagesFromSearchText("сова")
    }

    @Test
    fun repositoryTest() {

        Mockito.verify(presenter.repository, Mockito.times(1))
    }

}