package com.cosmetics.lush.ui.homemodule.cart.delivery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.home.delivermethods.Quote
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ItemDeliveryCodeChildBindingImpl

class DeliveryMethodChildAdapter(
    private var itemList: List<Quote>,
    private val viewModel: DeliveryMethodViewModel
) : RecyclerView.Adapter<CommonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val view: ItemDeliveryCodeChildBindingImpl =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_delivery_code_child,
                parent,
                false
            )
        return CommonViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        val map: Map<Int, Any> = mapOf(BR.quote to itemList[position], BR.viewModel to viewModel)
        holder.bind(map)
    }

    fun updateList(quotes: List<Quote>) {
        this.itemList = quotes
        notifyDataSetChanged()
    }


}
