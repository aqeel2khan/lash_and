package com.cosmetics.lush.utils

import android.content.res.Resources
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.cosmetics.core.utils.withFont

@BindingAdapter("error")
fun EditText.setError(error: Int) {
    try {
        setError(context.getString(error).withFont(context))
        requestFocus()
    } catch (e: Resources.NotFoundException) {
        setError(null)
        clearFocus()
    }

}