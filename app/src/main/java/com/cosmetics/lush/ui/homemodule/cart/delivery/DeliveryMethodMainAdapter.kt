package com.cosmetics.lush.ui.homemodule.cart.delivery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.home.delivermethods.ShippingMethod
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ItemDeliveryMethodMainBinding

class DeliveryMethodMainAdapter(
    private var itemList: List<ShippingMethod>,
    private val viewModel: DeliveryMethodViewModel
) : RecyclerView.Adapter<DeliveryMethodMainAdapter.DeliveryMethodMainVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryMethodMainVH {
        val view: ItemDeliveryMethodMainBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_delivery_method_main,
                parent,
                false
            )
        return DeliveryMethodMainVH(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: DeliveryMethodMainVH, position: Int) {
        holder.bind(itemList[position])
    }

    fun updateList(shippingMethods: List<ShippingMethod>) {
        this.itemList = shippingMethods
        notifyDataSetChanged()
    }

    inner class DeliveryMethodMainVH(private val binder: ItemDeliveryMethodMainBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(shippingMethod: ShippingMethod) {
            binder.shippingMethod = shippingMethod
            binder.recyclerViewDeliverySubType.adapter = DeliveryMethodChildAdapter(
                shippingMethod.quote,
                viewModel
            )
        }
    }

}

class CommonViewHolder(private val binder: ViewDataBinding) :
    RecyclerView.ViewHolder(binder.root) {
    fun bind(id: Map<Int, Any>) {
        id.forEach { (i, any) ->
            binder.setVariable(i, any)
        }
        binder.executePendingBindings()
    }

    fun bind(id: Int, data: Any) {
        binder.setVariable(id, data)
        binder.executePendingBindings()
    }
}