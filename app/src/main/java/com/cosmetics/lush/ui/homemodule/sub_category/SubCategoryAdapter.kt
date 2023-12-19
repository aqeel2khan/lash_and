package com.cosmetics.lush.ui.homemodule.sub_category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.home.request.Category
import com.cosmetics.lush.R
import com.cosmetics.lush.databinding.SubCategoryItemBinding
import kotlinx.android.synthetic.main.sub_category_item.view.*

class SubCategoryAdapter(
    private val subCategoryList: List<Category>,
    private val onSubCategorySelectedListener: OnSubCategorySelectedListener
) :
    RecyclerView.Adapter<SubCategoryAdapter.SubCategoryVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryVH {
        val view: SubCategoryItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.sub_category_item,
                parent,
                false
            )
        return SubCategoryVH(view)
    }

    override fun getItemCount(): Int = subCategoryList.size

    override fun onBindViewHolder(holder: SubCategoryVH, position: Int) {
        holder.bind(position)
    }

    inner class SubCategoryVH(private val binder: SubCategoryItemBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(position: Int) {
            binder.categoryModel = subCategoryList[position]
            itemView.nextIV.visibility =
                if (position == 2 || position % 3 == 2) View.VISIBLE else View.GONE
            itemView.setOnClickListener {
                onSubCategorySelectedListener.onSubCategorySelected(subCategoryList[position])
            }
        }
    }

}

interface OnSubCategorySelectedListener {
    fun onSubCategorySelected(category: Category)
}
