package com.cosmetics.lush.ui.products.product_details.productoption.viewholder

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.core.utils.selectTime
import com.cosmetics.domain.model.product.Option
import com.cosmetics.lush.databinding.ItemTimeProductOptionsBinding
import com.cosmetics.lush.ui.products.product_details.productoption.ProductOptionsViewModel

class ProductOptionTimeViewHolder(
    private val viewModel: ProductOptionsViewModel,
    private val binder: ItemTimeProductOptionsBinding
) : RecyclerView.ViewHolder(binder.root), OptionBinder {
    override fun bind(option: Option) {
        binder.option = option
        binder.viewModel = viewModel
        binder.textViewDateText.setOnClickListener {
            selectTime(it) { time ->
                option.value = time
                viewModel.setOptionData(option.productOptionId.toString(), time)
                if (it is TextView) {
                    it.text = time
                }
            }
        }
    }
}