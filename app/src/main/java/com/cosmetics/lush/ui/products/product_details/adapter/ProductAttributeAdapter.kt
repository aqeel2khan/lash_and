package com.cosmetics.lush.ui.products.product_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.Attribute
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ItemProductAttributeBinding
import com.cosmetics.lush.ui.homemodule.cart.delivery.CommonViewHolder
import kotlinx.android.synthetic.main.item_product_attribute.view.*

class ProductAttributeAdapter(private val itemList: List<Attribute>) :
    RecyclerView.Adapter<CommonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val view: ItemProductAttributeBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product_attribute,
                parent,
                false
            )
        return CommonViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        var attribute = itemList[position]
        holder.bind(BR.attribute, attribute)
        holder.itemView.viewClickEnablerAttribute.setOnClickListener {
            attribute.isExpanded = !attribute.isExpanded
            notifyDataSetChanged()
        }
    }

}