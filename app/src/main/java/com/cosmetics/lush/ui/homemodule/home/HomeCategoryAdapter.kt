package com.cosmetics.lush.ui.homemodule.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.home.request.Category
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.homemodule.sub_category.OnSubCategorySelectedListener
import kotlinx.android.synthetic.main.home_category_item.view.*

class HomeCategoryAdapter(
    var data: List<Category>,
    private val onSubCategorySelectedListener: OnSubCategorySelectedListener,
    val onClick: (category: Category) -> Unit
) :
    RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryVH>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    // private val categoryItemList = DummyData(context).getCategoryItemList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_category_item, parent, false)
        return HomeCategoryVH(view)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemId(position: Int): Long {
        return data[position].categoryId.toLong()
    }

    override fun onBindViewHolder(holder: HomeCategoryVH, position: Int) {
        holder.bind(data[position])
    }

    fun updateItems(data: List<Category>) {
        this.data = data
        notifyDataSetChanged()
    }


    inner class HomeCategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(categoryItem: Category) {
            itemView.categoryTitleTV.text = categoryItem.formattedName
            itemView.subCategoryRV.apply {
                if (adapter == null) {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    val homeSubCategoryAdapter = HomeSubCategoryAdapter(
                        categoryItem.categories, onSubCategorySelectedListener
                    )
                    homeSubCategoryAdapter.setHasStableIds(true)
                    adapter = homeSubCategoryAdapter
                } else {
                    (adapter as HomeSubCategoryAdapter).updateList(categoryItem.categories)
                }
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
                setRecycledViewPool(viewPool)
            }
            itemView.setOnClickListener {
                onClick(categoryItem)
            }
        }
    }
}