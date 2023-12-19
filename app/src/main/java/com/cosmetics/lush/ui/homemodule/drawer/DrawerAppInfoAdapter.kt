package com.cosmetics.lush.ui.homemodule.drawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.home.appinfo.AppInformation
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.drawer_category_item.view.*

class DrawerAppInfoAdapter(
    private val appInformationList: List<AppInformation>,
    val onClick: (appInformation: AppInformation) -> Unit
) :
    RecyclerView.Adapter<DrawerAppInfoAdapter.DrawerCategoryVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerCategoryVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.drawer_category_item, parent, false)
        return DrawerCategoryVH(view)
    }

    override fun getItemCount(): Int = appInformationList.size

    override fun onBindViewHolder(holder: DrawerCategoryVH, position: Int) {
        holder.bind(appInformationList[position])
    }

    inner class DrawerCategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(appInformation: AppInformation) {
            itemView.categoryNameTV.text = appInformation.getFormattedTitle()
            itemView.setOnClickListener {
                onClick(appInformation)
            }
        }
    }
}