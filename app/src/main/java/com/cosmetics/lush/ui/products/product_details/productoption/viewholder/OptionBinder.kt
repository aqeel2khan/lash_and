package com.cosmetics.lush.ui.products.product_details.productoption.viewholder

import com.cosmetics.domain.model.product.Option

interface OptionBinder {
    fun bind(option: Option)
}