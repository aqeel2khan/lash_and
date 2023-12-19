package com.cosmetics.lush.ui.common_views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.cart_counter.view.*

class CartCounter(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context!!, attrs) {
    private var count = 4

    init {
        val view = View.inflate(context, R.layout.cart_counter, this)
        countTV.text = count.toString()
        incrementIV.setOnClickListener {
            if (count < 10) {
                count++
                countTV.text = count.toString()
            }
        }

        decrementIV.setOnClickListener {
            if (count > 0) {
                count--
                countTV.text = count.toString()
            }
        }
    }

    fun setCount(value: Int) {
        count = value
        countTV.text = count.toString()
    }

    interface ProductCountClickListener {
        fun onProductCountClick(count: Int)
    }
}