package com.dex.gallery.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.AbsListView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.dex.gallery.R
import com.dex.gallery.adapter.AlbumFoldersAdapter
import com.dex.gallery.data.Albums
import com.dex.gallery.eventListener.IOnItemClick
import kotlinx.android.synthetic.main.include_main_content.*

class MainActivity : AppCompatActivity(), IOnItemClick {

    private var folderName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null)
            folderName = savedInstanceState.getString("folderName")

//        setSupportActionBar(my_toolbar)
        // Enable the Up button
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_menu))

//        setupNavigationView()

        val extra = intent.extras
        if (extra != null) {
            val extraData = extra.get("image_url_data") as ArrayList<Albums>
            selectFragment(extraData)
        }

//        drawerLayoutListener()
        supportActionBar?.title = "Gallery"
    }


    private fun selectFragment(imagesList: ArrayList<Albums>) {

        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .override(160, 160)
            .skipMemoryCache(true)
            .error(R.drawable.ic_broken_image)

        val glide = Glide.with(this)
        val builder = glide.asBitmap()
        rvAlbums?.layoutManager = GridLayoutManager(this, 2)
        rvAlbums?.setHasFixedSize(true)

        // AlbumFoldersAdapter.kt is RecyclerView Adapter class. we will implement shortly.
        rvAlbums?.adapter = AlbumFoldersAdapter(imagesList, this, options, builder, glide, this)

        rvAlbums?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

//        fab_camera?.setOnClickListener { launchCamera() }
    }

//    private fun drawerLayoutListener() {
//
//        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
//            override fun onDrawerStateChanged(newState: Int) {
//            }
//
//            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
//            }
//
//            override fun onDrawerClosed(drawerView: View) {
//                supportActionBar?.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_menu))
//            }
//
//            override fun onDrawerOpened(drawerView: View) {
//                supportActionBar?.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_keyboard_backspace))
//            }
//        }
//        )
//    }

    // Navigation item click listener Kotlin source code.
//    private fun setupNavigationView() {
//
//        navigation.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
//            override fun onNavigationItemSelected(item: MenuItem): Boolean {
//                drawer_layout.closeDrawer(Gravity.START)
//                when (item.itemId) {
//                    R.id.nav_all_folders -> {
//                    }
//                    R.id.nav_hidden_folders -> {
//                    }
//                }
//                return false
//            }
//        })
//    }

    override fun onItemClick(position: String, isVideo: Boolean) {
        val bundle = Bundle()
        bundle.putString("folderName", position)
        val intent = Intent(this, AlbumsActivity::class.java)
        intent.putExtra("folderName", position)
        startActivity(intent)
    }
}
