package com.cosmetics.domain.model.home.profile


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartCountResponse(
    @SerializedName("data")
    val cartCount: CartCount
) : Parcelable, BaseResponse()

@Parcelize
data class CartCount(
    @SerializedName("total_product_count")
    val totalProductCount: Int
) : Parcelable