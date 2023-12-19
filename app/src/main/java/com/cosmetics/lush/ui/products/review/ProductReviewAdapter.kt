package com.cosmetics.lush.ui.products.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.product.ReviewItem
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.ProductRatingItemBinding

class ProductReviewAdapter(
    private val reviewItemList: List<ReviewItem>
) : RecyclerView.Adapter<ProductReviewAdapter.ProductReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductReviewViewHolder {
        val view: ProductRatingItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_rating_item,
            parent,
            false
        )
        return ProductReviewViewHolder(view)
    }

    override fun getItemCount(): Int = reviewItemList.size

    override fun onBindViewHolder(holder: ProductReviewViewHolder, position: Int) {
        holder.bind(reviewItemList[position])
    }

    inner class ProductReviewViewHolder(private val binder: ProductRatingItemBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(reviewItem: ReviewItem) {
            binder.review = reviewItem
        }

    }
}