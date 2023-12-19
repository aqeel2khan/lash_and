package com.cosmetics.lush.ui.homemodule.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.core.utils.showSnackBarLessTime
import com.cosmetics.domain.model.product.cart.ProductInCart
import com.cosmetics.domain.model.product.cart.UpdateCartRequest
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.CartListItemBinding

class MyCartAdapter(
    private val activity: FragmentActivity?,
    private val products: MutableList<ProductInCart>,
    private val onCountButtonClick: (count: Int, productInCart: ProductInCart) -> Unit
) :
    RecyclerView.Adapter<MyCartAdapter.CartVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val view: CartListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cart_list_item,
            parent,
            false
        )
        return CartVH(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        holder.bindView(products[position])
    }

    fun updateList(updateCartRequest: UpdateCartRequest?) {
        // products.removeIf{ it == account.id }
        val iterator = products.iterator()
        while (iterator.hasNext()) {
            val it = iterator.next()
            if (it.key == updateCartRequest?.key) {
                it.quantity = updateCartRequest.quantity
                if (it.quantity == 0) {
                    iterator.remove()
                }
                notifyDataSetChanged()
            }
        }
    }

    inner class CartVH(private val binder: CartListItemBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bindView(product: ProductInCart) {
            binder.product = product
            binder.cartCounter.incrementIV.setOnClickListener {
                var count = product.quantity + 1
                onCountButtonClick(count, product)
            }
            binder.cartCounter.decrementIV.setOnClickListener {
                var count = product.quantity - 1
                if (count < 0) count = 0
                if (count < product.minimum) {
                    activity?.showSnackBarLessTime(
                        String.format(
                            activity.getString(R.string.this_product_has_a_minmium_value),
                            product.minimum
                        )
                    )
                } else {
                    onCountButtonClick(count, product)
                }
            }
            binder.deleteIV.setOnClickListener {
                var count = 0
                onCountButtonClick(count, product)
            }
        }
    }
}