package com.dex.gallery.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dex.gallery.R
import kotlinx.android.synthetic.main.activity_photo.*

class SingleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        setSupportActionBar(toolbar)
        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val folderName = intent.getStringExtra("folder_name")
        Glide.with(this).load(folderName).into(imageFullScreenView)

//        Handler().postDelayed(
//            {
//                if (supportActionBar != null)
//                    appbar.animate().translationY(-appbar.bottom.toFloat()).setInterpolator(AccelerateInterpolator()).start()
//                var isAppBarShown = false
//            }, 1500
//        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId
        return super.onOptionsItemSelected(item)
    }
}