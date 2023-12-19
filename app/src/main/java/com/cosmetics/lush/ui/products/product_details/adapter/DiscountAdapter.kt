package com.cosmetics.lush.ui.products.product_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.Discount
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.DiscountListItemBinding
import com.cosmetics.lush.ui.homemodule.cart.delivery.CommonViewHolder

class DiscountAdapter(private val itemList: List<Discount>) :
    RecyclerView.Adapter<CommonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val view: DiscountListItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.discount_list_item,
                parent,
                false
            )
        return CommonViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        holder.bind(BR.discount, itemList[position])
    }

}