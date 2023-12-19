package com.cosmetics.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

fun Context.convertDpToPixels(dp: Float) =
    dp * resources.displayMetrics.density

fun Context.getFont(@FontRes fontId: Int): Typeface? {
    return try {
        ResourcesCompat.getFont(this, fontId)
    } catch (e: java.lang.Exception) {
        Typeface.DEFAULT
    }
}

fun Context.isNetworkAvailable(): Boolean {

    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
}

fun String.withFont(context: Context?): RegularSpannableString {
    return RegularSpannableString(context!!, this)
}

fun String.toast(context: Context) {

    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.toastLong(context: Context) {

    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

@SuppressLint("NewApi")
fun String.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this.trim()).matches()
}