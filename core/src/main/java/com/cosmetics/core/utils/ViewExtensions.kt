package com.cosmetics.core.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.LayerDrawable
import android.text.TextUtils
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.Group
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.viewpager.widget.ViewPager
import com.cosmetics.core.R
import com.cosmetics.core.commonviews.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.center_title_toolbar.view.*

fun View.hideKeyboard() {
    try {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun TextView.strikeThrough() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun EditText.setFocusToEnd() {
    this.setSelection(this.text.length)
}

fun TextView.underLine() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun EditText.verifyNull(): Boolean {
    return TextUtils.isEmpty(this.text.toString().trim())
}

fun EditText.getTrimText(): String {
    return text.toString().trim()
}


fun EditText.isDigits(): Boolean {
    return TextUtils.isDigitsOnly(this.text.toString().trim())
}

fun EditText.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this.text.toString().trim()).matches()
}

fun Activity.setUpChatCount(count: Int, menuItem: MenuItem?) {
    menuItem?.let {
        val icon = it.icon as LayerDrawable
        val badge: BadgeDrawable

        // Reuse drawable if possible
        val reuse = icon.findDrawableByLayerId(R.id.ic_group_count)
        badge = if (reuse != null && reuse is BadgeDrawable) {
            reuse
        } else {
            BadgeDrawable(this)
        }
        badge.setCount(count.toString())
        icon.mutate()
        icon.setDrawableByLayerId(R.id.ic_group_count, badge)
    }

}

fun AppCompatActivity.setUpBackToolbar(toolbar: View, title: String = "") {
    setSupportActionBar(toolbar as Toolbar)
    supportActionBar?.apply {
        setTitle("")
        setDisplayShowTitleEnabled(false)
        setDisplayHomeAsUpEnabled(true)
    }
    toolbar.titleTV.text = title
}

fun View.rotate(startAngle: Float, endAngle: Float) {
    ValueAnimator
        .ofFloat(startAngle, endAngle).apply {
            duration = 300
            addUpdateListener { rotation = it.animatedValue as Float }
        }.start()
}

fun ViewGroup.openWithCircularReveal() {
    val anim = ViewAnimationUtils.createCircularReveal(
        this, right, bottom, 0f,
        Math.hypot(width.toDouble(), height.toDouble()).toFloat()
    ).apply {
        duration = 300
        interpolator = LinearInterpolator()
        doOnStart { visibility = View.VISIBLE }
    }
    anim.start()
}

fun ViewGroup.closeWithCircularReveal() {
    val anim = ViewAnimationUtils.createCircularReveal(
        this, right, bottom, Math.max(width, height).toFloat(), 0f
    ).apply {
        duration = 300
        interpolator = LinearInterpolator()
        doOnEnd { visibility = View.INVISIBLE }
    }
    anim.start()
}

inline fun ViewPager.addOnPageChangeListener(
    crossinline onScrollStateChanged: (state: Int) -> Unit = {},
    crossinline onScrolled: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit = { _, _, _ -> },
    crossinline onSelected: (position: Int) -> Unit = {}
): ViewPager.OnPageChangeListener {
    val listener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) = onScrollStateChanged(state)
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) =
            onScrolled(position, positionOffset, positionOffsetPixels)

        override fun onPageSelected(position: Int) = onSelected(position)
    }
    addOnPageChangeListener(listener)
    return listener
}

inline fun ViewPager.doOnPageSelected(crossinline action: (position: Int) -> Unit) =
    addOnPageChangeListener(onSelected = action)

inline fun Group.setOnAllItemClickListener(crossinline action: () -> Unit) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id)?.setOnClickListener { action() }
    }
}

inline fun TabLayout.addOnTabSelectedListener(
    crossinline onReselected: (tab: TabLayout.Tab?) -> Unit = {},
    crossinline onUnSelected: (tab: TabLayout.Tab?) -> Unit = {},
    crossinline onSelected: (tab: TabLayout.Tab?) -> Unit = {}
) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) = onReselected(tab)

        override fun onTabUnselected(tab: TabLayout.Tab?) = onUnSelected(tab)

        override fun onTabSelected(tab: TabLayout.Tab?) = onSelected(tab)
    })
}




