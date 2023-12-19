package com.cosmetics.lush.imageloading

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide


class ImageLoaderImpl : ImageLoader {
    override fun createNormalShapedImage(
        view: ImageView,
        url: String?,
        drawable: Drawable?
    ) {
        /* view.load(url) {
             crossfade(true)
             placeholder(R.drawable.ic_placeholder)
             error(R.drawable.ic_placeholder)
         }*/
        Glide
            .with(view.context)
            .load(url)
            .placeholder(drawable)
            .into(view)

        /*   view.load(url) {
               crossfade(true)
               placeholder(drawable)
           }*/
    }


    override fun createCircularShapedImage(view: ImageView, uri: Uri) {
        /*  view.load(uri) {
              crossfade(true)
              placeholder(R.drawable.ic_placeholder)
              transformations(CircleCropTransformation())
              scale(Scale.FILL)
          }*/
        /*      Glide.with(view.context).load(uri).transform(CircleCrop())
                  .placeholder(R.drawable.ic_arrow_back).error(R.drawable.ic_placeholder).into(view)
         */


    }


    override fun createCircularShapedImage(view: ImageView, url: String) {
        /*view.load(url) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
            transformations(CircleCropTransformation())
            scale(Scale.FILL)
        }*/
        /*     Glide.with(view.context).load(url).transform(CircleCrop())
                 .placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder).into(view)
        */
    }

}