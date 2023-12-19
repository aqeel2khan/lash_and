package com.cosmetics.lush.ui.homemodule.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.cart.Product
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.CheckoutListItemBinding
import com.cosmetics.lush.ui.homemodule.cart.delivery.CommonViewHolder

class CheckOutAdapter(private var itemList: List<Product>) :
    RecyclerView.Adapter<CommonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val view: CheckoutListItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.checkout_list_item,
                parent,
                false
            )
        return CommonViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        holder.bind(BR.model, itemList[position])
    }

    fun updateList(product: List<Product>) {
        this.itemList = product
        notifyDataSetChanged()
    }


}