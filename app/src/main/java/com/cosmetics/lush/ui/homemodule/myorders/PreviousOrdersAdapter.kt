package com.cosmetics.lush.ui.homemodule.myorders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.lush.R

class PreviousOrdersAdapter : RecyclerView.Adapter<PreviousOrdersAdapter.PreviousOrdersVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviousOrdersVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.previous_order_item, parent, false)
        return PreviousOrdersVH(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: PreviousOrdersVH, position: Int) {
    }

    inner class PreviousOrdersVH(itemView: View) : RecyclerView.ViewHolder(itemView)
}