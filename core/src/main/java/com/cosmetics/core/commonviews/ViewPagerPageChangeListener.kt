package com.cosmetics.core.commonviews

import androidx.viewpager.widget.ViewPager

interface ViewPagerPageChangeListener : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        onViewPagerPageSelected(position)
    }

    fun onViewPagerPageSelected(position: Int)
}