package com.cosmetics.domain.model.order


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.util.getDateFromTimeStamp
import com.cosmetics.domain.util.getTimeFromTimeStamp
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderHistoryResponse(
    @SerializedName("data")
    val order: List<Order>
) : Parcelable, BaseResponse()

@Parcelize
data class Order(
    @SerializedName("currency")
    val currency: Currency,
    @SerializedName("currency_code")
    val currencyCode: String,
    @SerializedName("currency_value")
    val currencyValue: String,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("products")
    val products: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("total_raw")
    val totalRaw: String
) : Parcelable {

    fun getDateFormated() = getDateFromTimeStamp(timestamp)
    fun getTimeFormated() = getTimeFromTimeStamp(timestamp)

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