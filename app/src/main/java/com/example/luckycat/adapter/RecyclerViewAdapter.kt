package com.example.luckycat.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.luckycat.R
import com.example.luckycat.network.CatProperty

class RecyclerViewAdapter: PagingDataAdapter<CatProperty, RecyclerViewAdapter.MyViewHolder>(DiffUtilCallBack())  {
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)

        return MyViewHolder(inflater)
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val imageView: ImageView = view.findViewById(R.id.cat_image)
        val text = view.findViewById<TextView>(R.id.cat_history)

        fun bind(data: CatProperty) {
            text.text = data.imgSrcUrl
            Glide.with(imageView)
                .load(data.imgSrcUrl)
                .into(imageView)
        }
    }
    class DiffUtilCallBack: DiffUtil.ItemCallback<CatProperty>() {
        override fun areItemsTheSame(oldItem: CatProperty, newItem: CatProperty): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatProperty, newItem: CatProperty): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.imgSrcUrl == newItem.imgSrcUrl
        }

    }
}