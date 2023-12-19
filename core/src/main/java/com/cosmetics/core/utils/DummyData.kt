package com.cosmetics.core.utils

import android.content.Context
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class DummyData(
    private val context:
    Context
) {



    private val orderStatusArray = arrayOf(
        "Order Placed", "Order picked by the delivery team",
        "Order has cleared verification process", "Order is nearby", "Order is delivered"
    )


    @Parcelize
    data class CategoryItem(
        val categoryName: String,
        val subCategoryName: String,
        val categoryImage: Int
    ) : Parcelable

}

data class OrderStatusItem(
    val statusName: String,
    val status: Boolean
)
