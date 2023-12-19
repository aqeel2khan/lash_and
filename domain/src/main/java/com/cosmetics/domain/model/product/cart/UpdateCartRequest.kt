package com.cosmetics.domain.model.product.cart


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateCartRequest(
    @SerializedName("key")
    val key: String,
    @SerializedName("quantity")
    val quantity: Int
) : Parcelable