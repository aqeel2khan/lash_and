package com.cosmetics.domain.model.product

import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductItem(
    @SerializedName("data")
    val data: Product
) : Parcelable, BaseResponse()

