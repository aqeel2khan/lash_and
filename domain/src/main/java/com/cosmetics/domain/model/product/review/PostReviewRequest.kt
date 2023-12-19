package com.cosmetics.domain.model.product.review


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostReviewRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("text")
    val text: String,
    val productId: Int
) : Parcelable