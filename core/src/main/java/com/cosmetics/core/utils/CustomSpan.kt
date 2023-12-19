package com.cosmetics.core.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

class CustomSpan(var font: Typeface?) : TypefaceSpan("") {

    override fun updateDrawState(ds: TextPaint) {
        applyTypeFace(ds, font!!)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyTypeFace(paint, font!!)
    }

    private fun applyTypeFace(paint: Paint, tf: Typeface) {
        paint.typeface = tf
    }
}
