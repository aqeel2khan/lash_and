package com.cosmetics.domain.model.cart


import com.google.gson.annotations.SerializedName

data class AddCouponRequest(
    @SerializedName("coupon")
    val coupon: String
)