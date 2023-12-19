package com.cosmetics.lush.ui.common_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ProductCounterBinding

class ProductCounter(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var binding: ProductCounterBinding? = null

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.product_counter,
            this,
            true
        )
    }

    fun getBinder(): ProductCounterBinding? {
        return binding
    }
}