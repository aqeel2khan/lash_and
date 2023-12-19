package com.cosmetics.lush.ui.homemodule.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.cart.Total
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.PriceSplitupItemBinding
import com.cosmetics.lush.ui.homemodule.cart.delivery.CommonViewHolder

class CheckoutPriceSplitUpAdapter(private var itemList: List<Total>) :
    RecyclerView.Adapter<CommonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val view: PriceSplitupItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.price_splitup_item,
                parent,
                false
            )
        return CommonViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        holder.bind(BR.model, itemList[position])
    }

    fun updateList(total: List<Total>) {
        this.itemList = total
        notifyDataSetChanged()
    }
}
