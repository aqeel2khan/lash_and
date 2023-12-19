package com.cosmetics.lush.ui.products.product_details.productoption.viewholder

import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.Option
import com.cosmetics.lush.databinding.ItemDropDownProductOptionsBinding
import com.cosmetics.lush.ui.products.product_details.productoption.ProductOptionsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductOptionDropdownViewHolder(
    private val viewModel: ProductOptionsViewModel,
    private val binder: ItemDropDownProductOptionsBinding
) :
    RecyclerView.ViewHolder(binder.root), OptionBinder {
    override fun bind(option: Option) {
        binder.option = option
        binder.viewModel = viewModel
        binder.textViewSelectionTitle.setOnClickListener {
            binder.spinnerSelection.performClick()
        }
        binder.spinnerSelection.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    option.activePositionIndex = position
                    with(option) {
                        viewModel.setOptionData(
                            productOptionId.toString(),
                            optionValue[position - 1].productOptionValueId
                        )
                    }
                } else {
                    with(option) {
                        viewModel.removeFromOptionData(
                            productOptionId.toString()
                        )
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
    }

    private suspend fun setDefaultSelection(
        option: Option,
        spinnerSelection: AppCompatSpinner
    ) {
        withContext(Dispatchers.IO) {
            option.optionValue.forEach {
                /*if (option.value==it.name){
                    se
                }*/
            }
        }
    }
}