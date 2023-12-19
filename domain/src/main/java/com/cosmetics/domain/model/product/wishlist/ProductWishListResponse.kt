package com.cosmetics.domain.model.product.wishlist


import android.annotation.SuppressLint
import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.home.store.valueFromHtml
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ProductWishListResponse(
    @SerializedName("data")
    val productWishList: List<ProductWishList>
) : Parcelable, BaseResponse()


@Parcelize
data class ProductWishList(
    @SerializedName("model")
    val model: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("product_seo_url")
    val productSeoUrl: String,
    @SerializedName("special")
    val special: String,
    @SerializedName("stock")
    val stock: String,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("category_id")
    val category_id: Int,
    @SerializedName("rating")
    val rating: Int = 0,
    @SerializedName("reviews")
    val reviews: String = "0"
) : Parcelable {
    val formattedName: String
        get() = name.valueFromHtml()

    private fun isSpecialPriceAvailable(): Boolean {
        if (!special.isNullOrEmpty() && !special.equals("0", true)) {
            return true
        }
        return false
    }

    fun getFinalePrice(): String {
        if (isSpecialPriceAvailable()) {
            return special
        }
        return price
    }
}