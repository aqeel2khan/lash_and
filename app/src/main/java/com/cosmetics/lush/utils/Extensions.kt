package com.cosmetics.lush.utils

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import androidx.navigation.NavController

inline fun withDrawerDelay(crossinline withDrawerDelay: () -> Unit) {
    Handler().postDelayed({ withDrawerDelay() }, 300)
}

inline fun withLessDelay(crossinline withDrawerDelay: () -> Unit) {
    Handler().postDelayed({ withDrawerDelay() }, 150)
}

fun <T : Parcelable> NavController.navigateWithBundle(key: String, id: Int, item: T) {
    var bundle = Bundle()
    bundle.putParcelable(key, item)
    navigate(id, bundle)
}

