package com.home.searchimage.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.home.searchimage.databinding.ImageListItemBinding
import com.home.searchimage.ui.main.MainPresenter
import com.home.searchimage.ui.main.OnItemViewClickListener

class MainRVAdapter(
    val presenter: MainPresenter,
    private var onItemViewClickListener : OnItemViewClickListener?
) : RecyclerView.Adapter<MainRVAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            ImageListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return presenter.getCount()
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
        holder.bind(presenter.users[position])
    }


    inner class viewHolder(val vb: UserLoginsItemBinding) : RecyclerView.ViewHolder(vb.root),
        UserItemView {
        override var pos: Int = -1
        override fun setLogin(text: String) {
            vb.tvLogin.text = text
        }
        fun bind(userItem: GithubUser){
            itemView.setOnClickListener{
                onItemViewClickListener?.onItemViewClick(userItem.login)
            }
        }
    }
}
