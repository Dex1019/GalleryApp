package com.dex.gallery

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_layout.view.*

class SingleAlbumAdapter(
    val context: Context,
    val albumList: MutableList<String>,
    val options: RequestOptions,
    val glide: RequestBuilder<Bitmap>,
    val glideMain: RequestManager,
    val inOnItemClick: IOnItemClick
) : RecyclerView.Adapter<SingleAlbumAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(albumList.get(position), glide, options, inOnItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_single_album_layout, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(
            albumList: String,
            glide: RequestBuilder<Bitmap>,
            options: RequestOptions,
            inOnItemClick: IOnItemClick
        ) {

            glide.load(albumList).apply { options }.thumbnail(0.4f)
                .into(itemView.thumbnail)

            itemView.setOnClickListener { inOnItemClick.onItemClick(albumList, false) }
        }
    }
}