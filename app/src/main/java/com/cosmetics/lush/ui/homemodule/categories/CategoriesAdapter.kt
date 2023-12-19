package com.cosmetics.lush.ui.homemodule.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.home.request.Category
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.CategoriesItemBinding

class CategoriesAdapter(
    private val categoryItemList: List<Category>,
    private val onCategoryClickListener: OnCategoryClickListener
) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesVH>() {

    //  private val categoryItemList = DummyData(context).getCategoryItemList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesVH {
        val view: CategoriesItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.categories_item,
                parent,
                false
            )
        return CategoriesVH(view)
    }

    override fun getItemCount(): Int = categoryItemList.size

    override fun onBindViewHolder(holder: CategoriesVH, position: Int) {
        holder.bind(categoryItemList[position])
    }

    inner class CategoriesVH(private val binder: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(categoryItem: Category) {
            binder.categoryModel = categoryItem
            /*    itemView.categoryIV.setImageResource(categoryItem.categoryImage)
                itemView.categoriesTV.text = categoryItem.categoryName
                itemView.setOnClickListener {
                    onCategoryClickListener.onCategoryClickListener(
                        categoryItem
                    )
                }*/
            itemView.setOnClickListener {
                onCategoryClickListener.onCategoryClickListener(
                    categoryItem
                )
            }
        }
    }

    interface OnCategoryClickListener {
        fun onCategoryClickListener(categoryItem: Category)
    }
}