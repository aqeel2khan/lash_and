package com.cosmetics.lush.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.core.utils.strikeThrough
import com.cosmetics.domain.model.product.Product
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ProductGridItemBinding
import com.cosmetics.lush.databinding.ProductListItemBinding


private const val TYPE_LIST = 0
private const val TYPE_GRID = 1

class ProductsAdapter(
    private var product: MutableList<Product>,
    private val gridLayoutManager: GridLayoutManager,
    private val onProductsClickListener: OnProductsClickListener,
    var addToWishList: (Int, Int) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductsVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsVH {
        val view: ViewDataBinding = if (viewType == TYPE_LIST) {
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.product_list_item,
                parent,
                false
            )
        } else {
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.product_grid_item,
                parent,
                false
            )
        }
        return ProductsVH(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (gridLayoutManager.spanCount == 1)
            TYPE_LIST
        else
            TYPE_GRID
    }

    override fun getItemCount(): Int = product.size

    override fun onBindViewHolder(holder: ProductsVH, position: Int) {
        val product = product[position]
        holder.bindView(product, position)
        holder.itemView.setOnClickListener { onProductsClickListener.onProductClick(product) }
    }

    fun updateFavourites(position: Int) {
        product[position]
    }

    fun updateList(products: MutableList<Product>) {
        product = products
        notifyDataSetChanged()
    }

    inner class ProductsVH(private val binder: ViewDataBinding) : RecyclerView.ViewHolder(
        binder.root
    ) {
        fun bindView(product: Product, position: Int) {
            if (binder is ProductGridItemBinding) {
                binder.productOriginalPriceTV.strikeThrough()
                binder.product = product
            } else if (binder is ProductListItemBinding) {
                binder.product = product
                binder.productOriginalPriceTV.strikeThrough()
            }
        }
    }

    interface OnProductsClickListener {
        fun onProductClick(product: Product)
    }
}