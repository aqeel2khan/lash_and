package com.cosmetics.domain.model.product.cart


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentMethodResponse(
    @SerializedName("data")
    val data: PaymentMethodList
) : Parcelable, BaseResponse()

@Parcelize
data class PaymentMethodList(
    @SerializedName("payment_methods")
    val paymentMethods: List<PaymentMethod>
) : Parcelable

@Parcelize
data class PaymentMethod(
    @SerializedName("code")
    val code: String,
    @SerializedName("sort_order")
    val sortOrder: String,
    @SerializedName("terms")
    val terms: String,
    @SerializedName("title")
    val title: String,
    var isSelected: Boolean = false
) : Parcelable