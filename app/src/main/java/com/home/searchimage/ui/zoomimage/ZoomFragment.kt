package com.home.searchimage.ui.zoomimage

import android.app.PictureInPictureUiState
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Picture
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableResource
import com.bumptech.glide.load.resource.bitmap.BitmapResource
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.home.searchimage.R
import com.home.searchimage.ZoomableImageView

import com.home.searchimage.databinding.ImageZoomFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.NullPointerException
import javax.annotation.Resource
import android.graphics.Bitmap.createBitmap as createBitmap


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

    override fun setDefaultImage() {
        viewBinding.zoomImageView.setImageResource(R.drawable.ic_baseline_image_100)
    }

    override fun downloadClick() {
        viewBinding.downloadButton.setOnClickListener(View.OnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                saveImage(
                    Glide.with(this@ZoomFragment)
                        .asBitmap()
                        .load(getURLFromBundle()) // sample image
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal) // need placeholder to avoid issue like glide annotations
                        .error(android.R.drawable.stat_notify_error) // need error to avoid issue like glide annotations
                        .submit()
                        .get()
                )
            }
            Toast(requireContext()).apply {
                setText("Изображение скачано")
                show()
            }
        })
    }

    private fun getImageFileName(getURL: String): String {
        val split: List<String> = getURL.split("/")
        val length = split.size
        return split[length - 1]
    }


    private fun saveImage(image: Bitmap): String? {
        var savedImagePath: String? = null
        val imageFileName = "JPEG_${getImageFileName(getURLFromBundle())}"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/Search image"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.getAbsolutePath()
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath)
            //Toast.makeText(this, "IMAGE SAVED", Toast.LENGTH_LONG).show() // to make this working, need to manage coroutine, as this execution is something off the main thread
        }
        return savedImagePath
    }

    private fun galleryAddPic(imagePath: String?) {
        imagePath?.let { path ->
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val f = File(path)
            val contentUri: Uri = Uri.fromFile(f)
            mediaScanIntent.data = contentUri
            requireActivity().sendBroadcast(mediaScanIntent)
        }
    }

    override fun setZoomImage() {
        val zoomableImageView = viewBinding.zoomImageView
        Glide
            .with(this)
            .asBitmap()
            .load(getURLFromBundle())
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    zoomableImageView.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    fun getURLFromBundle(): String {
        val bundle = getArguments()
        val URLFromBundle = bundle?.getString("key")
        if (URLFromBundle != null)
            return URLFromBundle
        else {
            throw NullPointerException()
        }
    }

}
