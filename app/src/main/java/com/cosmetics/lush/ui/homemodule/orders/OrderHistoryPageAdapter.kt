package com.cosmetics.lush.ui.homemodule.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.ViewState
import com.cosmetics.domain.model.order.Order
import com.cosmetics.domain.model.product.Product
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.CurrentOrderItemBinding
import com.cosmetics.lush.utils.NetworkStateItemViewHolder


private const val TYPE_LIST = 0
private const val TYPE_GRID = 1
private const val TYPE_ERROR = 2

class OrderHistoryPageAdapter(
    private val retryCallback: () -> Unit,
    private val itemClick: (order: Order) -> Unit
) : PagedListAdapter<Order, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    private var networkState: ViewState? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: CurrentOrderItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.current_order_item,
                parent,
                false
            )
        return if (viewType == TYPE_ERROR) {
            NetworkStateItemViewHolder.create(parent, retryCallback)
        } else {
            OrderHistoryVH(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            hasExtraRow() && position == itemCount - 1 -> {
                TYPE_ERROR
            }
            else -> {
                TYPE_LIST
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_ERROR -> (holder as NetworkStateItemViewHolder).bindTo(networkState)
            else -> {
                val order = getItem(position)
                order?.let {
                    (holder as OrderHistoryVH).bindView(it)
                }
            }
        }

    }

    inner class OrderHistoryVH(private val binder: CurrentOrderItemBinding) :
        RecyclerView.ViewHolder(
            binder.root
        ) {
        fun bindView(order: Order) {
            binder.order = order
            binder.root.setOnClickListener { itemClick(order) }
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Order>() {
            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
                oldItem.orderId == newItem.orderId
        }
    }
}