package com.cosmetics.lush.ui.homemodule.cart.paymentmethod

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.cart.PaymentMethod
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ItemPaymentMethodBinding
import com.cosmetics.lush.ui.homemodule.cart.delivery.CommonViewHolder

class PaymentMethodAdapter(
    private var itemList: List<PaymentMethod>,
    private val viewModel: PaymentMethodViewModel
) : RecyclerView.Adapter<CommonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val view: ItemPaymentMethodBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_payment_method,
                parent,
                false
            )
        return CommonViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        val map: Map<Int, Any> =
            mapOf(BR.paymentMethod to itemList[position], BR.viewModel to viewModel)
        holder.bind(map)
    }

    fun updateList(paymentMethods: List<PaymentMethod>) {
        this.itemList = paymentMethods
        notifyDataSetChanged()
    }


}
