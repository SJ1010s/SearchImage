package com.home.searchimage.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.home.searchimage.R
import com.home.searchimage.model.data.ImageMainScreenData
import com.home.searchimage.databinding.ImageListItemBinding
import com.home.searchimage.ui.main.MainPresenter
import com.home.searchimage.ui.main.OnItemViewClickListener

class MainRVAdapter(
    val presenter: MainPresenter,
    private var onItemViewClickListener: OnItemViewClickListener?
) : RecyclerView.Adapter<MainRVAdapter.viewHolder>() {

    private var imageList: List<ImageMainScreenData>? = null

    fun setImageList(images: List<ImageMainScreenData>) {
        imageList = images
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            ImageListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        if (imageList != null) {
            return imageList!!.size
        } else {
            return 0
        }
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        if (imageList != null) {
            holder.bind(imageList!![position])
        }
    }


    inner class viewHolder(val vb: ImageListItemBinding) : RecyclerView.ViewHolder(vb.root) {

        fun bind(imageItem: ImageMainScreenData) {
            itemView.setOnClickListener {

                onItemViewClickListener?.onItemViewClick(imageItem.largeImageURL)
            }
            Glide
                .with(itemView.context)
                .load(imageItem.previewURL)
                .into(itemView.findViewById(R.id.rv_image))
            itemView.findViewById<TextView>(R.id.eye_count_text).text = imageItem.views.toString()
            itemView.findViewById<TextView>(R.id.download_count_text).text =
                imageItem.downloads.toString()
        }
    }
}
