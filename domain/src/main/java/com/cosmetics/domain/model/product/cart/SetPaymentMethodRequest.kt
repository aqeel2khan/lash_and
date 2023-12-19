package com.cosmetics.domain.model.product.cart


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SetPaymentMethodRequest(
    @SerializedName("agree")
    val agree: String = "1",
    @SerializedName("comment")
    val comment: String,
    @SerializedName("payment_method")
    val paymentMethod: String
) : Parcelable