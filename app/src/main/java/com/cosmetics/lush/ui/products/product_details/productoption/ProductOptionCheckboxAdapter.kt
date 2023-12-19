package com.cosmetics.lush.ui.products.product_details.productoption

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.OptionValue
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ItemCheckBoxProductOptionsBinding


class ProductOptionCheckboxAdapter(
    private val productOptionMainId: Int,
    private val optionValueList: List<OptionValue>,
    private val viewModel: ProductOptionsViewModel
) : RecyclerView.Adapter<ProductOptionCheckboxAdapter.ProductOptionCheckBoxViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProductOptionCheckboxAdapter.ProductOptionCheckBoxViewHolder {
        return ProductOptionCheckBoxViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_check_box_product_options,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = optionValueList.size

    override fun onBindViewHolder(holder: ProductOptionCheckBoxViewHolder, position: Int) {
        holder.bind(optionValueList[position])
    }

    inner class ProductOptionCheckBoxViewHolder(
        private val binder: ItemCheckBoxProductOptionsBinding
    ) : RecyclerView.ViewHolder(binder.root) {
        fun bind(option: OptionValue) {
            binder.optionValue = option
            binder.viewModel = viewModel
            binder.checkboxProductOption.isChecked = option.isChecked
            binder.checkboxProductOption.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.onCheckedChange(productOptionMainId, option, isChecked)
                option.isChecked = isChecked
            }
        }
    }

}