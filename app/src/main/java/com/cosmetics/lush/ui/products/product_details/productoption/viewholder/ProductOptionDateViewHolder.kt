package com.cosmetics.lush.ui.products.product_details.productoption.viewholder

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.core.utils.selectDate
import com.cosmetics.domain.model.product.Option
import com.cosmetics.lush.databinding.ItemDateProductOptionsBinding
import com.cosmetics.lush.ui.products.product_details.productoption.ProductOptionsViewModel

class ProductOptionDateViewHolder(
    private val viewModel: ProductOptionsViewModel,
    private val binder: ItemDateProductOptionsBinding
) :
    RecyclerView.ViewHolder(binder.root), OptionBinder {
    override fun bind(option: Option) {
        binder.option = option
        binder.viewModel = viewModel
        binder.textViewDateText.setOnClickListener {
            selectDate(it) { date ->
                option.value = date
                viewModel.setOptionData(option.productOptionId.toString(), date)
                if (it is TextView) {
                    it.text = date
                }
            }
        }
    }
}