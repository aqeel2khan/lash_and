package com.cosmetics.lush.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.ViewState
import com.cosmetics.core.utils.strikeThrough
import com.cosmetics.domain.model.product.Product
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ProductGridItemBinding
import com.cosmetics.lush.databinding.ProductListItemBinding
import com.cosmetics.lush.utils.NetworkStateItemViewHolder


private const val TYPE_LIST = 0
private const val TYPE_GRID = 1
private const val TYPE_ERROR = 2

class ProductsPageAdapter(
    private val gridLayoutManager: GridLayoutManager,
    private val onProductsClickListener: OnProductsClickListener,
    private val retryCallback: () -> Unit
) : PagedListAdapter<Product, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    private var networkState: ViewState? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: ViewDataBinding =
            when (viewType) {
                TYPE_LIST -> {
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.product_list_item,
                        parent,
                        false
                    )
                }
                TYPE_GRID -> {
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.product_grid_item,
                        parent,
                        false
                    )

                }
                else -> {
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.network_state_item,
                        parent,
                        false
                    )
                }
            }
        return if (viewType == TYPE_ERROR) {
            NetworkStateItemViewHolder.create(parent, retryCallback)
        } else {
            ProductsVH(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            hasExtraRow() && position == itemCount - 1 -> {
                TYPE_ERROR
            }
            gridLayoutManager.spanCount == 1 -> {
                TYPE_LIST
            }
            else -> {
                TYPE_GRID
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_ERROR -> (holder as NetworkStateItemViewHolder).bindTo(networkState)
            else -> {
                val product = getItem(position)
                product?.let {
                    (holder as ProductsVH).bindView(it, position)
                }
            }
        }

    }

    inner class ProductsVH(private val binder: ViewDataBinding) : RecyclerView.ViewHolder(
        binder.root
    ) {
        fun bindView(product: Product, position: Int) {
            if (binder is ProductGridItemBinding) {
                binder.productOriginalPriceTV.strikeThrough()
                binder.product = product
            } else if (binder is ProductListItemBinding) {
                binder.productOriginalPriceTV.strikeThrough()
                binder.product = product
            }
            itemView.setOnClickListener { onProductsClickListener.onProductClick(product) }
        }
    }

    interface OnProductsClickListener {
        fun onProductClick(product: Product)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow() = networkState != null && networkState != Completed

    fun setNetworkState(newNetworkState: ViewState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id
        }
    }
}