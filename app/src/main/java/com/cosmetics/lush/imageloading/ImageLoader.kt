package com.cosmetics.lush.imageloading

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView

interface ImageLoader {
    fun createNormalShapedImage(view: ImageView, url: String?, drawable: Drawable?)
    fun createCircularShapedImage(view: ImageView, url: String)
    fun createCircularShapedImage(view: ImageView, uri: Uri)

}