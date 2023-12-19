package com.cosmetics.domain.model.product.cart


import com.google.gson.annotations.SerializedName

data class AddProductToCartRequest(
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("option")
    var mutableMap: HashMap<String, Any>?
)


