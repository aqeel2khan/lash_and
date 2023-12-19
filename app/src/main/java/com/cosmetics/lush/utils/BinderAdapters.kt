package com.cosmetics.lush.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.cosmetics.core.utils.rotate
import com.cosmetics.domain.di.getKoinInstance
import com.cosmetics.domain.model.product.WISH_LIST_ADDED
import com.cosmetics.domain.model.product.WISH_LIST_GONE
import com.cosmetics.domain.model.product.WISH_LIST_NOT_ADDED
import com.cosmetics.domain.model.product.cart.ProductInCart
import com.cosmetics.lush.R
import com.cosmetics.lush.imageloading.ImageLoader
import com.cosmetics.lush.ui.products.product_details.ProductImageAdapter
import com.facebook.shimmer.ShimmerFrameLayout

object ImageBinderAdapters {

    private val imageLoader: ImageLoader =
        getKoinInstance()

    @JvmStatic
    @BindingAdapter("concatenateStringData")
    fun concatenateStringData(view: TextView, data: String?) {
        data?.let {

        }
    }

    @JvmStatic
    @BindingAdapter("startShimmer")
    fun startShimmer(view: ShimmerFrameLayout, isDataAvailable: Boolean) {
        if (!isDataAvailable) {
            view.startShimmer()
        } else {
            view.stopShimmer()
            view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("setImage")
    fun loadNormalImage(image: ImageView, imageUrl: String?) {
        imageUrl?.let {
            imageLoader.createNormalShapedImage(
                image, imageUrl,
                ContextCompat.getDrawable(image.context, R.drawable.placeholder_category)
            )
        }
    }

    @JvmStatic
    @BindingAdapter("setImageWithPlaceholder", "placeholder")
    fun loadNormalImageWithPlaceholder(image: ImageView, imageUrl: String?, drawable: Drawable) {
        imageUrl?.let {
            imageLoader.createNormalShapedImage(
                image, imageUrl,
                drawable
            )
        }
    }
    /*
    @JvmStatic
    @BindingAdapter("setVisibility")
    fun loadNormalImage(view: View, isNeeded: Boolean) {
        imageUrl?.let { imageLoader.createNormalShapedImage(view, it) }
    }*/

    @JvmStatic
    @BindingAdapter("setHomeImage")
    fun loadHomeImage(view: ImageView, imageUrl: String?) {
        imageUrl?.let { imageLoader.createCircularShapedImage(view, it) }
    }

    @JvmStatic
    @BindingAdapter("setImageSlider")
    fun setImageSlider(view: ViewPager, imageUrl: List<String>?) {
        view.adapter = ProductImageAdapter(imageUrl)
    }

    @JvmStatic
    @BindingAdapter("addressCountVisibility")
    fun setAddressCountView(view: View, count: String?) {
        count?.let {
            if (it.toInt() < 1) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("editTextActivate")
    fun editTextActivate(view: EditText, isActive: Boolean) {
        view.isClickable = !isActive
        view.isFocusable = !isActive
        view.isFocusableInTouchMode = !isActive
    }

    @JvmStatic
    @BindingAdapter("viewDataBasedVisibility")
    fun setViewVisibility(view: View, data: String?) {
        if (data.isNullOrEmpty()) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("animateDropDown")
    fun handleExpandableViewClick(dropDownIV: ImageView, data: Boolean) {
        if (data) {
            dropDownIV.rotate(0f, 180f)
        } else {
            dropDownIV.rotate(180f, 0f)
        }
    }

    @JvmStatic
    @BindingAdapter("viewGroupDataBasedVisibility")
    fun setViewGroupVisibility(view: Group, data: Boolean) {
        if (!data) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("viewDataBooleanBasedVisibility")
    fun setViewDataBooleanBasedVisibility(view: View, data: Boolean) {
        if (!data) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("cartTittleWithErrorCode")
    fun cartTittleWithErrorCode(view: TextView, product: ProductInCart) {
        var title = product.name
        if (!product.stock) {
            title = "$title ***"
            val spannable: Spannable =
                SpannableString(title)
            spannable.setSpan(
                ForegroundColorSpan(Color.RED),
                title.length - 3, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            view.text = spannable
        } else {
            view.text = title
        }
    }

    @JvmStatic
    @BindingAdapter("loadHtml")
    fun setLoadHtml(webview: WebView, html: String?) {
        html?.let {
            webview.settings.javaScriptEnabled = true
            webview.loadDataWithBaseURL("", html, "text/html", "UTF-8", "")
        }
    }
    /*
    @JvmStatic
    @BindingAdapter("loadHtml")
    fun setLoadHtml(webview: WebView, html: String?) {
        html?.let {
            webview.settings.javaScriptEnabled = true
            webview.loadDataWithBaseURL("", html, "text/html", "UTF-8", "")
        }
    }*/

    @JvmStatic
    @BindingAdapter("addNewAddressVisibility")
    fun addNewAddressVisibility(view: View, count: String?) {
        count?.let {
            if (it.toInt() < 1) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("addNewAddressVisibility")
    fun addNewAddressViewGroupVisibility(view: ViewGroup, count: String?) {
        count?.let {
            if (it.toInt() < 1) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setWishListImage")
    fun handleWishListImage(imageView: ImageView, product: Int) {
        product.let {
            when (it) {
                WISH_LIST_ADDED -> imageView.setDrawable(R.drawable.ic_my_favourite)
                WISH_LIST_NOT_ADDED -> imageView.setDrawable(R.drawable.ic_favourite)
                WISH_LIST_GONE -> imageView.visibility = View.GONE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setSpinnerAdapter")
    fun setSpinnerAdapter(spinner: AppCompatSpinner, array: Array<String>?) {
        if (array != null && spinner.adapter == null) {
            val adapter =
                ArrayAdapter(spinner.context, R.layout.spinner_item_top, array)
            adapter.setDropDownViewResource(R.layout.spinner_item_bottom)
            spinner.adapter = adapter
        }
    }

    @JvmStatic
    @BindingAdapter("goneUnlessOnMotionLayout")
    fun goneUnlessOnMotionLayout(
        view: View,
        visible: Boolean
    ) {
        if (view.parent is MotionLayout) {
            val layout = view.parent as MotionLayout
            val setToVisibility =
                if (visible) View.VISIBLE else View.GONE
            val setToAlpha = if (visible) 1f else 0f
            for (constraintId in layout.constraintSetIds) {
                val constraint: ConstraintSet? = layout.getConstraintSet(constraintId)
                if (constraint != null) {
                    constraint.setVisibility(view.id, setToVisibility)
                    constraint.setAlpha(view.id, setToAlpha)
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setProductOptionSpinnerAdapter", "setActivePosition")
    fun setProductOptionSpinnerAdapter(
        spinner: AppCompatSpinner,
        array: Array<String>?,
        activePosition: Int
    ) {
        if (array != null) {
            val adapter =
                ArrayAdapter(spinner.context, R.layout.spinner_item_white_top, array)
            adapter.setDropDownViewResource(R.layout.spinner_item_bottom)
            spinner.adapter = adapter
            spinner.setSelection(activePosition)
        }
    }

    @JvmStatic
    @BindingAdapter("zoneVisibility")
    fun setZoneVisibility(view: View, array: Array<String>?) {
        if (array == null) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }


    @JvmStatic
    @BindingAdapter("productCheckoutTextStyle")
    fun setProductCheckoutTextStyle(view: TextView, title: String) {
        if (title.equals("Total", false)) {
            view.setTextAppearance(R.style.ProductCheckoutTextStyleBold)
        } else {
            view.setTextAppearance(R.style.ProductCheckoutTextStyle)
        }
    }

    private fun ImageView.setDrawable(id: Int) {
        visibility = View.VISIBLE
        setImageDrawable(ContextCompat.getDrawable(context, id))
    }


}
