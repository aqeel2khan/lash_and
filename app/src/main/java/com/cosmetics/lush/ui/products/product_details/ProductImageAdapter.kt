package com.cosmetics.lush.ui.products.product_details

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.cosmetics.domain.di.getKoinInstance
import com.cosmetics.lush.R
import com.cosmetics.lush.imageloading.ImageLoader


class ProductImageAdapter(private val imageUrl: List<String>?) : PagerAdapter() {
    private val imageLoader: ImageLoader =
        getKoinInstance()

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ImageView
    }

    override fun getCount(): Int {
        return imageUrl?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val image = ImageView(container.context)
        imageLoader.createNormalShapedImage(
            image, imageUrl?.get(position),
            ContextCompat.getDrawable(container.context, R.drawable.placeholder_product)
        )
        container.addView(image)
        return image
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }
}