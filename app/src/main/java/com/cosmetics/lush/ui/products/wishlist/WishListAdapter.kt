package com.cosmetics.lush.ui.products.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.wishlist.ProductWishList
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.WishListItemBinding

class WishListAdapter(
    private val productWishList: MutableList<ProductWishList>,
    var wishListRemoval: (Int, Int) -> Unit,
    var onClick: (Int, Int) -> Unit
) : RecyclerView.Adapter<WishListAdapter.WishListVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListVH {
        val view: WishListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.wish_list_item,
            parent,
            false
        )
        return WishListVH(view)
    }

    override fun getItemCount(): Int = productWishList.size

    override fun onBindViewHolder(holder: WishListVH, position: Int) {
        holder.bind(productWishList[position], position)
    }

    fun updateWishList(
        viewModel: WishListViewModel,
        position: Int
    ) {
        productWishList.removeAt(position)
        if (productWishList.isNullOrEmpty()) viewModel.noRecordsFound.value = true
        notifyItemRemoved(position)
    }

    inner class WishListVH(private val binder: WishListItemBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(productWishList: ProductWishList, position: Int) {
            binder.product = productWishList
            binder.imageViewRemove.setOnClickListener {
                wishListRemoval(productWishList.productId, adapterPosition)
            }
            itemView.setOnClickListener {
                onClick(productWishList.productId, productWishList.category_id)
            }
        }

    }
}