package com.cosmetics.lush.ui.products.product_details.productoption.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.Option
import com.cosmetics.lush.databinding.ItemCheckBoxItemBinding
import com.cosmetics.lush.ui.products.product_details.productoption.ProductOptionCheckboxAdapter
import com.cosmetics.lush.ui.products.product_details.productoption.ProductOptionsViewModel

class ProductOptionCheckBoxViewHolder(
    private val viewModel: ProductOptionsViewModel,
    private val binder: ItemCheckBoxItemBinding
) :
    RecyclerView.ViewHolder(binder.root), OptionBinder {
    override fun bind(option: Option) {
        binder.recyclerViewCheckBoxes.adapter =
            ProductOptionCheckboxAdapter(option.productOptionId, option.optionValue, viewModel)
        binder.option = option
    }
}