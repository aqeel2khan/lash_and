package com.cosmetics.lush.ui.products.product_details.productoption.viewholder

import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.Option
import com.cosmetics.lush.databinding.ItemTextareaProductOptionsBinding
import com.cosmetics.lush.ui.products.product_details.productoption.ProductOptionsViewModel

class ProductOptionTextAreaViewHolder(
    private val viewModel: ProductOptionsViewModel,
    private val binder: ItemTextareaProductOptionsBinding
) : RecyclerView.ViewHolder(binder.root), OptionBinder {
    override fun bind(option: Option) {
        binder.option = option
        binder.viewModel = viewModel
        binder.editTextTextArea.doAfterTextChanged {
            option.value = it.toString()
            viewModel.setOptionData(option.productOptionId.toString(), it.toString())
        }
    }
}