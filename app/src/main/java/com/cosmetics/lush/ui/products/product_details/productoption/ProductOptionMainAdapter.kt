package com.cosmetics.lush.ui.products.product_details.productoption

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.products.product_details.productoption.viewholder.*

class ProductOptionMainAdapter(private val viewModel: ProductOptionsViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val checkboxText = "checkbox"
    private val dateText = "date"
    private val dateTimeText = "datetime"
    private val selectText = "select"
    private val timeText = "time"
    private val textAreaText = "textarea"
    private val commentText = "text"
    private val checkbox = 1
    private val date = 2
    private val dateTime = 3
    private val select = 4
    private val time = 5
    private val textArea = 6
    private val comment = 7
    private val productOptions = viewModel.product.options

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            checkbox -> {
                ProductOptionCheckBoxViewHolder(
                    viewModel,
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_check_box_item,
                        parent,
                        false
                    )
                )
            }
            date -> {
                ProductOptionDateViewHolder(
                    viewModel,
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_date_product_options,
                        parent,
                        false
                    )
                )
            }
            dateTime -> {
                ProductOptionDateTimeViewHolder(
                    viewModel,
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_date_product_options,
                        parent,
                        false
                    )
                )
            }
            time -> {
                ProductOptionTimeViewHolder(
                    viewModel,
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_time_product_options,
                        parent,
                        false
                    )
                )
            }
            textArea -> {
                ProductOptionTextAreaViewHolder(
                    viewModel,
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_textarea_product_options,
                        parent,
                        false
                    )
                )
            }
            comment -> {
                ProductOptionCommentViewHolder(
                    viewModel,
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_coment_product_options,
                        parent,
                        false
                    )
                )
            }
            else -> {
                ProductOptionDropdownViewHolder(
                    viewModel,
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_drop_down_product_options,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = productOptions.size

    override fun getItemViewType(position: Int): Int {
        return when (productOptions[position].type) {
            checkboxText -> checkbox
            dateText -> date
            dateTimeText -> dateTime
            selectText -> select
            timeText -> time
            commentText -> comment
            textAreaText -> textArea
            else -> select
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OptionBinder -> {
                Log.i("Bug Position", position.toString())
                holder.bind(viewModel.product.options[position])
            }
        }
    }

}