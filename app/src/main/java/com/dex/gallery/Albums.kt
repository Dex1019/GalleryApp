package com.dex.gallery

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Albums(var folderNames: String,
                  var imagePath: String,
                  var imgCount: Int,
                  var isVideo: Boolean) : Parcelable