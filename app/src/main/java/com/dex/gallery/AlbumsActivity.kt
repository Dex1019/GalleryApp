package com.dex.gallery

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.AbsListView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_albums.*

class AlbumsActivity : AppCompatActivity(), IOnItemClick {

    var adapter: SingleAlbumAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        setSupportActionBar(my_album_toolbar)
        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val folderName = intent.getStringExtra("folder_name")
        supportActionBar?.title = "" + folderName
        val isVideo = intent.getBooleanExtra("isVideo", false)
        initViews(folderName, isVideo)
    }


    private fun initViews(folderName: String?, isVideo: Boolean?) {

        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(160, 160)
            .skipMemoryCache(true)
            .error(R.drawable.ic_broken_image)
        val glide = Glide.with(this)
        val builder = glide.asBitmap()

        rvAlbumSelected.layoutManager = GridLayoutManager(this, 2)
        rvAlbumSelected?.setHasFixedSize(true)
        adapter = SingleAlbumAdapter(
            this,
            getAllShownImagesPath(this, folderName, isVideo),
            options,
            builder,
            glide,
            this
        )
        rvAlbumSelected?.adapter = adapter

        rvAlbumSelected?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> glide.resumeRequests()
                    AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL,
                    AbsListView.OnScrollListener.SCROLL_STATE_FLING -> glide.pauseRequests()
                }
            }
        }
        )
    }


    // Read all images path from specified directory.

    private fun getAllShownImagesPath(activity: Activity, folderName: String?, isVideo: Boolean?): MutableList<String> {

        val uri: Uri
        val cursorBucket: Cursor
        val columnIndexData: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String? = null

        val selectionArgs = arrayOf("%" + folderName + "%")

        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Images.Media.DATA + " like ? "

        val projectionOnlyBucket = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        cursorBucket = activity.contentResolver.query(uri, projectionOnlyBucket, selection, selectionArgs, null)

        columnIndexData = cursorBucket.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        while (cursorBucket.moveToNext()) {
            absolutePathOfImage = cursorBucket.getString(columnIndexData)
            if (absolutePathOfImage != "" && absolutePathOfImage != null)
                listOfAllImages.add(absolutePathOfImage)
        }
        return listOfAllImages.asReversed()

    }

    override fun onItemClick(position: String, isVideo: Boolean) {
        val intent = Intent(this, SingleActivity::class.java)
        intent.putExtra("folder_name", position)
        startActivity(intent)
    }
}
