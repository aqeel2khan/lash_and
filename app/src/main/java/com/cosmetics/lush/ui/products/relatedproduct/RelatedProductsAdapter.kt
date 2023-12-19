package com.cosmetics.lush.ui.products.relatedproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.Product
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ProductListItemBinding
import com.cosmetics.lush.ui.products.ProductsPageAdapter

class RelatedProductsAdapter(
    private val product: MutableList<Product>,
    private val onProductsClickListener: ProductsPageAdapter.OnProductsClickListener
) : RecyclerView.Adapter<RelatedProductsAdapter.RelatedProductsVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedProductsVH {
        val view: ProductListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_list_item,
            parent,
            false
        )
        return RelatedProductsVH(view)
    }

    override fun getItemCount(): Int = product.size

    override fun onBindViewHolder(holder: RelatedProductsVH, position: Int) {
        holder.bind(product[position], position)
    }

    fun updateWishList(
        position: Int
    ) {
        product.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class RelatedProductsVH(private val binder: ProductListItemBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(product: Product, position: Int) {
            binder.product = product
            itemView.setOnClickListener {
                onProductsClickListener.onProductClick(product)
            }
        }

    }
}