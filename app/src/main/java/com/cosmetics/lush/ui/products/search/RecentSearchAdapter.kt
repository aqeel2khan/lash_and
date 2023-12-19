package com.cosmetics.lush.ui.products.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.recent_search_item.view.*

class RecentSearchAdapter(
    private val list: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<RecentSearchAdapter.RecentSearchVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recent_search_item, parent, false)
        return RecentSearchVH(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecentSearchVH, position: Int) {
        holder.bind(holder, position)
    }

    inner class RecentSearchVH(private var view: View) : RecyclerView.ViewHolder(view) {
        fun bind(holder: RecentSearchVH, position: Int) {
            itemView.recentTV.text = list[position]
            itemView.setOnClickListener {
                onClick(itemView.recentTV.text.toString())
            }
        }
    }
}