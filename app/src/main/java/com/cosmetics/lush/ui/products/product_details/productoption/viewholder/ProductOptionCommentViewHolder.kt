package com.cosmetics.lush.ui.products.product_details.productoption.viewholder

import android.view.MotionEvent
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.Option
import com.cosmetics.lush.databinding.ItemComentProductOptionsBinding
import com.cosmetics.lush.ui.products.product_details.productoption.ProductOptionsViewModel

class ProductOptionCommentViewHolder(
    private val viewModel: ProductOptionsViewModel,
    private val binder: ItemComentProductOptionsBinding
) :
    RecyclerView.ViewHolder(binder.root), OptionBinder {
    override fun bind(option: Option) {
        binder.option = option
        binder.viewModel = viewModel
        setScrollViewAdapter(binder.editTextTextArea)
        binder.editTextTextArea.doAfterTextChanged {
            option.value = it.toString()
            viewModel.setOptionData(option.productOptionId.toString(), it.toString())
        }
    }

    private fun setScrollViewAdapter(editText: EditText) {
        editText.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v.parent
                    .requestDisallowInterceptTouchEvent(false)
            }
            false
        }
    }
}