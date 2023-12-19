package com.cosmetics.domain.model.order

import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderStatusResponse(
    @SerializedName("data")
    val orderStatus: List<OrderStatus>
) : Parcelable, BaseResponse()


@Parcelize
data class OrderStatus(
    @SerializedName("name")
    val name: String,
    @SerializedName("order_status_id")
    val orderStatusId: Int
) : Parcelable

