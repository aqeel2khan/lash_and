package com.cosmetics.lush.ui.homemodule.drawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.home.request.Category
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.drawer_category_item.view.*

class DrawerCategoryAdapter(
    private val categoryList: List<Category>,
    val onClick: (category: Category) -> Unit
) :
    RecyclerView.Adapter<DrawerCategoryAdapter.DrawerCategoryVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerCategoryVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.drawer_category_item, parent, false)
        return DrawerCategoryVH(view)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: DrawerCategoryVH, position: Int) {
        holder.bind(categoryList[position])
    }

    inner class DrawerCategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(category: Category) {
            itemView.categoryNameTV.text = category.formattedName
            itemView.setOnClickListener {
                onClick(category)
            }
        }
    }
}