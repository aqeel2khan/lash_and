package com.cosmetics.domain.model.home.delivermethods


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeliveryMethodRequest(
    @SerializedName("comment")
    val comment: String,
    @SerializedName("shipping_method")
    val shippingMethod: String
) : Parcelable