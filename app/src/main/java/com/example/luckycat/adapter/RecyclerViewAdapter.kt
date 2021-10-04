package com.example.luckycat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.luckycat.R
import com.example.luckycat.network.CatProperty
import com.example.luckycat.ui.ItemClickListener

class RecyclerViewAdapter(private val itemClick: ItemClickListener) :
    PagingDataAdapter<CatProperty, RecyclerViewAdapter.MyViewHolder>(DiffUtilCallBack()) {
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)

        return MyViewHolder(inflater, itemClick)
    }

    class MyViewHolder(view: View, private val itemClick: ItemClickListener) :
        RecyclerView.ViewHolder(view) {

        private val imageView: ImageView = view.findViewById(R.id.cat_image)

        fun bind(data: CatProperty) {
            Glide.with(imageView)
                .load(data.imgSrcUrl)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(imageView)
            imageView.setOnClickListener {
                itemClick.onItemClick(data.imgSrcUrl)
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<CatProperty>() {
        override fun areItemsTheSame(oldItem: CatProperty, newItem: CatProperty): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatProperty, newItem: CatProperty): Boolean {
            return oldItem.id == newItem.id &&
                oldItem.imgSrcUrl == newItem.imgSrcUrl
        }
    }
}