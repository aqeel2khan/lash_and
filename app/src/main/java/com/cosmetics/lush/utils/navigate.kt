package com.cosmetics.lush.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.cosmetics.lush.R


fun Fragment.navigate(directions: NavDirections) {
    val options = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .setLaunchSingleTop(true)
        .build()
    NavHostFragment.findNavController(this).navigate(
        directions,
        options
    )
}