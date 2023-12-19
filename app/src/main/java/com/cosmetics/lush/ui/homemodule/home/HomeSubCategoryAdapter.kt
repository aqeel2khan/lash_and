package com.cosmetics.lush.ui.homemodule.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.home.request.Category
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.HomeSubCategoryItemBinding
import com.cosmetics.lush.ui.homemodule.sub_category.OnSubCategorySelectedListener

class HomeSubCategoryAdapter(
    private var categoryItem: List<Category>?,
    private val onSubCategorySelectedListener: OnSubCategorySelectedListener
) :
    RecyclerView.Adapter<HomeSubCategoryAdapter.HomeSubCategoryVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSubCategoryVH {

        val view: HomeSubCategoryItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.home_sub_category_item,
                parent,
                false
            )
        return HomeSubCategoryVH(view)
    }

    override fun getItemId(position: Int): Long {
        return categoryItem?.get(position)?.categoryId?.toLong() ?: super.getItemId(position)
    }

    override fun getItemCount(): Int = categoryItem?.size ?: 0

    override fun onBindViewHolder(holder: HomeSubCategoryVH, position: Int) {
        categoryItem?.get(position)?.let { holder.bind(it) }
    }

    fun updateList(categories: List<Category>) {
        categoryItem = categories
        notifyDataSetChanged()
    }

    inner class HomeSubCategoryVH(private val binder: HomeSubCategoryItemBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(categoryItem: Category) {
            binder.categoryModel = categoryItem
            itemView.setOnClickListener {
                onSubCategorySelectedListener.onSubCategorySelected(categoryItem)
            }
        }
    }
}