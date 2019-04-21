package com.dex.gallery.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.dex.gallery.R
import com.dex.gallery.data.Albums
import com.dex.gallery.eventListener.IOnItemClick
import kotlinx.android.synthetic.main.list_layout.view.*

class AlbumFoldersAdapter(
    val albumList: ArrayList<Albums>,
    val context: Context,
    val options: RequestOptions,
    val glide: RequestBuilder<Bitmap>,
    val glideMain: RequestManager,
    val inOnItemClick: IOnItemClick
) : RecyclerView.Adapter<AlbumFoldersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(albumList[position],
            glide,
            options,
            inOnItemClick,
            albumList[position].isVideo)

        holder.itemView.title?.text = albumList[position].folderNames
        if (albumList[position].isVideo)
            holder.itemView.photoCount?.text = "" + albumList[position].imgCount
        else
            holder.itemView.photoCount?.text = "" + albumList[position].imgCount
    }

    override fun getItemCount(): Int {
        return albumList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(
            albumList: Albums,
            glide: RequestBuilder<Bitmap>,
            options: RequestOptions,
            inOnItemClick: IOnItemClick,
            isVideo: Boolean
        ) {
            glide.load(albumList.imagePath)
                .apply { options }
                .thumbnail(0.4f)
                .into(itemView.thumbnail)

            itemView.setOnClickListener { inOnItemClick.onItemClick(albumList.folderNames, isVideo) }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        if (holder != null) {
            //glideMain.clear(holder.itemView.thumbnail)
            // glide.clear(holder.itemView.thumbnail)
            //Glide.get(context).clearMemory()
            // holder?.itemView?.thumbnail?.setImageBitmap(null)
        }// Glide.clear(holder?.itemView?.thumbnail)
        super.onViewRecycled(holder)

    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        if (holder != null) {
            // glideMain.clear(holder.itemView.thumbnail)
            //Glide.get(context).clearMemory()
            // holder?.itemView?.thumbnail?.setImageBitmap(null)

        }

        super.onViewDetachedFromWindow(holder)
    }

}