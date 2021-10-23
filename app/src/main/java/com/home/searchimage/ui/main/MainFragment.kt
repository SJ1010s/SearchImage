package com.home.searchimage.ui.main

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.github.terrakok.cicerone.Router
import com.home.searchimage.ImageSearch
import com.home.searchimage.R
import com.home.searchimage.databinding.MainFragmentBinding
import com.home.searchimage.model.Repository
import com.home.searchimage.model.data.ImageMainScreenData
import com.home.searchimage.model.room.AppDataBase
import com.home.searchimage.model.room.SearchRequestTable
import com.home.searchimage.ui.main.adapter.MainRVAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import retrofit2.Callback
import javax.inject.Inject

class MainFragment : MvpAppCompatFragment(), MainView {

    private var _viewBinding: MainFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val presenter: MainPresenter by moxyPresenter { MainPresenter() }
    private var adapter: MainRVAdapter? = null
    private var arrayAdapter: ArrayAdapter<String>? = null


    private var layoutManagerStateParcelable: Parcelable? = null

    @Inject
    lateinit var router: Router

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            layoutManagerStateParcelable =
                savedInstanceState.getParcelable("recycler_view_main_fragment")
        }
    }

    override fun onResume() {
        super.onResume()
        if (layoutManagerStateParcelable != null) {
            viewBinding.rvImages.layoutManager?.onRestoreInstanceState(layoutManagerStateParcelable)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = MainFragmentBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initRV() {
        viewBinding.rvImages.layoutManager = LinearLayoutManager(context)
        adapter = MainRVAdapter(presenter, presenter.itemViewClickListener)
        viewBinding.rvImages.adapter = adapter
    }

    override fun setImages(images: List<ImageMainScreenData>) {
        adapter?.setImageList(images)
    }

    override fun getInputSearchTextListener() {
        viewBinding.searchIcon.setOnClickListener {
            val getTextFromEditText = viewBinding.inputSearchText.text.toString().lowercase()
            presenter.getImagesFromSearchText(getTextFromEditText)
            presenter.setTextToDB(getTextFromEditText)

            onTextChange()

        }
        viewBinding.inputSearchText.setOnKeyListener { view, i, keyEvent ->
            val getTextFromEditText: String =
                viewBinding.inputSearchText.text.toString().lowercase()
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                (i == KeyEvent.KEYCODE_ENTER)
            ) {
                presenter.getImagesFromSearchText(getTextFromEditText)
                presenter.setTextToDB(getTextFromEditText)
                onTextChange()
                return@setOnKeyListener true;
            }
            return@setOnKeyListener false;
        }
    }


    override fun onTextChange() {
        arrayAdapter = ArrayAdapter<String>(
            requireActivity().getApplicationContext(),
            android.R.layout.simple_dropdown_item_1line,
            presenter.getAllFromDB()
        )
        val autoCompleteTextView = viewBinding.inputSearchText
        autoCompleteTextView.setAdapter(arrayAdapter)
    }


    override fun onPause() {
        super.onPause()
        layoutManagerStateParcelable = viewBinding.rvImages.layoutManager?.onSaveInstanceState()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            "recycler_view_main_fragment",
            viewBinding.rvImages.layoutManager?.onSaveInstanceState()
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
