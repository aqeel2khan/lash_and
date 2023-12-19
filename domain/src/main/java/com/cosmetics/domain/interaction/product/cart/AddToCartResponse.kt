package com.cosmetics.domain.interaction.product.cart


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddToCartResponse(
    @SerializedName("data")
    val data: Data
) : Parcelable, BaseResponse()

@Parcelize
data class Data(
    @SerializedName("product")
    val product: Product,
    @SerializedName("total")
    val total: String,
    @SerializedName("total_price")
    val totalPrice: String,
    @SerializedName("total_product_count")
    val totalProductCount: Int
) : Parcelable

@Parcelize
data class Product(
    @SerializedName("name")
    val name: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("quantity")
    val quantity: String
) : Parcelable, BaseResponse()