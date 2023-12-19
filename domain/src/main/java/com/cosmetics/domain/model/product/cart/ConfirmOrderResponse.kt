package com.cosmetics.domain.model.product.cart


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConfirmOrderResponse(
    @SerializedName("data")
    val data: Data
) : Parcelable, BaseResponse()


@Parcelize
data class Data(
    @SerializedName("order_id")
    val orderId: Int
) : Parcelable