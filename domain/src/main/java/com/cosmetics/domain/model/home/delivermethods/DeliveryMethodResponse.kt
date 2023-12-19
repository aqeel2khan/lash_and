package com.cosmetics.domain.model.home.delivermethods


import android.annotation.SuppressLint
import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class DeliveryMethodResponse(
    @SerializedName("data")
    val data: DeliveryMethods
) : Parcelable, BaseResponse()

@Parcelize
data class DeliveryMethods(
    @SerializedName("code")
    val code: String,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("shipping_methods")
    val shippingMethods: List<ShippingMethod>
) : Parcelable

@Parcelize
data class Quote(
    @SerializedName("code")
    val code: String,
    @SerializedName("cost")
    val cost: String,
    @SerializedName("tax_class_id")
    val taxClassId: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("title")
    val title: String,
    var isSelected: Boolean = false
) : Parcelable {

    fun getHeaderTitle() = "$title - $text"

}


@Parcelize
data class ShippingMethod(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("quote")
    val quote: List<Quote>,
    @SerializedName("sort_order")
    val sortOrder: String,
    @SerializedName("title")
    val title: String
) : Parcelable