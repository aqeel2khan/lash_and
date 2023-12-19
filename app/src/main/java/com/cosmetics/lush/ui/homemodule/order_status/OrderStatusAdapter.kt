package com.cosmetics.lush.ui.homemodule.order_status

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.domain.model.order.History
import com.cosmetics.lush.R
import kotlinx.android.synthetic.main.order_status_item.view.*

class OrderStatusAdapter(private val orderStatusList: MutableList<History>) :
    RecyclerView.Adapter<OrderStatusAdapter.OrderStatusVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderStatusVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_status_item, parent, false)
        return OrderStatusVH(view)
    }

    override fun getItemCount(): Int = orderStatusList.size

    override fun onBindViewHolder(holder: OrderStatusVH, position: Int) {
        holder.itemView.orderStatusTV.text = "- ${orderStatusList[position].status}"
        holder.itemView.orderStatusDateTV.text = "${orderStatusList[position].getDate()}"
        if (orderStatusList[position].isFound) {
            holder.itemView.orderStatusIV.visibility = View.VISIBLE
            holder.itemView.orderPathViewTop.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
            holder.itemView.orderStatusTV.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
        } else {
            holder.itemView.orderStatusIV.visibility = View.INVISIBLE
            holder.itemView.orderPathViewTop.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.grey_300
                )
            )
            holder.itemView.orderStatusTV.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.grey_400
                )
            )
        }
        holder.itemView.orderPathViewTop.visibility = if (position == 0) View.GONE else View.VISIBLE

        if (position < orderStatusList.size - 1) {
            if (orderStatusList[position + 1].isFound) {
                holder.itemView.orderPathViewBottom.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.black
                    )
                )
            } else {
                holder.itemView.orderPathViewBottom.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.grey_300
                    )
                )
            }
        } else if (position == orderStatusList.size - 1) {
            if (orderStatusList[position].isFound) {
                holder.itemView.orderPathViewBottom.visibility = View.GONE
            } else {
                holder.itemView.orderPathViewBottom.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.grey_300
                    )
                )
            }
        }
    }

    inner class OrderStatusVH(itemView: View) : RecyclerView.ViewHolder(itemView)
}