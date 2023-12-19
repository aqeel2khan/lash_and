package com.cosmetics.core.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import com.cosmetics.core.R


class RegularSpannableString(var context: Context, var source: CharSequence?) :
    SpannableString(source) {

    init {
        setSpan(
            CustomSpan(context.getFont(R.font.helvetica_neue)),
            0, source!!.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setSpan(RelativeSizeSpan(1f), 0, source!!.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

class MediumSpannableString(var context: Context, var source: CharSequence?) :
    SpannableString(source) {

    init {
        setSpan(
            CustomSpan(context.getFont(R.font.helvetica_neue_medium)),
            0, source!!.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setSpan(RelativeSizeSpan(1f), 0, source!!.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}