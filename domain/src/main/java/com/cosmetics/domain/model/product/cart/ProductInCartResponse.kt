package com.cosmetics.domain.model.product.cart


import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.databinding.BaseObservable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ProductInCartResponse(
    @SerializedName("data")
    val item: ItemList
) : Parcelable, BaseResponse()

@Parcelize
data class ItemList(
    @SerializedName("coupon")
    val coupon: String,
    @SerializedName("coupon_status")
    val couponStatus: Int,
    @SerializedName("currency")
    val currency: Currency,
    @SerializedName("has_download")
    val hasDownload: Int,
    @SerializedName("has_recurring_products")
    val hasRecurringProducts: Int,
    @SerializedName("has_shipping")
    val hasShipping: Int,
    @SerializedName("products")
    val products: MutableList<ProductInCart>,
    @SerializedName("reward")
    val reward: String,
    @SerializedName("reward_status")
    val rewardStatus: Boolean,
    @SerializedName("total")
    val total: String,
    @SerializedName("total_product_count")
    val totalProductCount: Int,
    @SerializedName("total_raw")
    val totalRaw: Double,
    @SerializedName("totals")
    val totals: List<Total>,
    @SerializedName("voucher")
    val voucher: String,
    @SerializedName("voucher_status")
    val voucherStatus: String,
    @SerializedName("vouchers")
    val vouchers: List<String>,
    @SerializedName("weight")
    val weight: String
) : Parcelable {
    fun getTotalAmount(): String {
        var total = ""
        totals.forEach {
            if (it.title == "Total") {
                total = it.text
                return total
            }
        }
        if (total.isNullOrEmpty()) {
            total = "KD $totalRaw"
        }
        return total
    }
}

@Parcelize
data class Currency(
    @SerializedName("currency_id")
    val currencyId: String,
    @SerializedName("decimal_place")
    val decimalPlace: String,
    @SerializedName("symbol_left")
    val symbolLeft: String,
    @SerializedName("symbol_right")
    val symbolRight: String,
    @SerializedName("value")
    val value: String
) : Parcelable


@Parcelize
data class ProductInCart(
    @SerializedName("minimum")
    val minimum: Int,
    @SerializedName("key")
    val key: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("points")
    val points: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("price_raw")
    val priceRaw: Double,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("quantity")
    var quantity: Int = 0,
    @SerializedName("recurring")
    val recurring: String,
    @SerializedName("reward")
    val reward: String,
    @SerializedName("stock")
    val stock: Boolean,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("total_raw")
    val totalRaw: Double
) : Parcelable, BaseObservable() {
    fun getProductCount(): String = quantity.toString()
}

@Parcelize
data class Total(
    @SerializedName("text")
    val text: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("value")
    val value: Double
) : Parcelable