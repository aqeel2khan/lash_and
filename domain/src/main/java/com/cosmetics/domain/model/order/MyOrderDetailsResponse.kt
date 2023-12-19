package com.cosmetics.domain.model.order

import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.util.getDateFromTimeStamp
import com.cosmetics.domain.util.getTimeFromTimeStamp
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyOrderDetailsResponse(
    @SerializedName("data")
    val data: OrderDetail
) : Parcelable, BaseResponse()


@Parcelize
data class OrderDetail(
    @SerializedName("comment")
    val comment: String,
    @SerializedName("currency")
    val currency: Currency,
    @SerializedName("currency_code")
    val currencyCode: String,
    @SerializedName("currency_id")
    val currencyId: String,
    @SerializedName("currency_value")
    val currencyValue: String,
    @SerializedName("customer_id")
    val customerId: String,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("date_modified")
    val dateModified: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("histories")
    val histories: MutableList<History>,
    @SerializedName("invoice_no")
    val invoiceNo: String,
    @SerializedName("invoice_prefix")
    val invoicePrefix: String,
    @SerializedName("ip")
    val ip: String,
    @SerializedName("language_id")
    val languageId: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("order_status_id")
    val orderStatusId: Int,
    @SerializedName("payment_address")
    val paymentAddress: String,
    @SerializedName("payment_address_1")
    val paymentAddress1: String,
    @SerializedName("payment_address_2")
    val paymentAddress2: String,
    @SerializedName("payment_address_format")
    val paymentAddressFormat: String,
    @SerializedName("payment_city")
    val paymentCity: String,
    @SerializedName("payment_company")
    val paymentCompany: String,
    @SerializedName("payment_country")
    val paymentCountry: String,
    @SerializedName("payment_country_id")
    val paymentCountryId: String,
    @SerializedName("payment_firstname")
    val paymentFirstname: String,
    @SerializedName("payment_iso_code_2")
    val paymentIsoCode2: String,
    @SerializedName("payment_iso_code_3")
    val paymentIsoCode3: String,
    @SerializedName("payment_lastname")
    val paymentLastname: String,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("payment_postcode")
    val paymentPostcode: String,
    @SerializedName("payment_zone")
    val paymentZone: String,
    @SerializedName("payment_zone_code")
    val paymentZoneCode: String,
    @SerializedName("payment_zone_id")
    val paymentZoneId: String,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("shipping_address")
    val shippingAddress: String,
    @SerializedName("shipping_address_1")
    val shippingAddress1: String,
    @SerializedName("shipping_address_2")
    val shippingAddress2: String,
    @SerializedName("shipping_address_format")
    val shippingAddressFormat: String,
    @SerializedName("shipping_city")
    val shippingCity: String,
    @SerializedName("shipping_company")
    val shippingCompany: String,
    @SerializedName("shipping_country")
    val shippingCountry: String,
    @SerializedName("shipping_country_id")
    val shippingCountryId: String,
    @SerializedName("shipping_firstname")
    val shippingFirstname: String,
    @SerializedName("shipping_iso_code_2")
    val shippingIsoCode2: String,
    @SerializedName("shipping_iso_code_3")
    val shippingIsoCode3: String,
    @SerializedName("shipping_lastname")
    val shippingLastname: String,
    @SerializedName("shipping_method")
    val shippingMethod: String,
    @SerializedName("shipping_postcode")
    val shippingPostcode: String,
    @SerializedName("shipping_zone")
    val shippingZone: String,
    @SerializedName("shipping_zone_code")
    val shippingZoneCode: String,
    @SerializedName("shipping_zone_id")
    val shippingZoneId: String,
    @SerializedName("store_id")
    val storeId: String,
    @SerializedName("store_name")
    val storeName: String,
    @SerializedName("store_url")
    val storeUrl: String,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("timestamp")
    val timestamp: String? = "",
    @SerializedName("total")
    val total: String = "",
    @SerializedName("totals")
    val totals: List<Total>
) : Parcelable {

    fun getDateFormated() = getDateFromTimeStamp(timestamp)
    fun getTimeFormated() = getTimeFromTimeStamp(timestamp)

}


@Parcelize
data class Product(
    @SerializedName("model")
    val model: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order_product_id")
    val orderProductId: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("price_raw")
    val priceRaw: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("return")
    val returnX: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("total_raw")
    val totalRaw: String,
    @SerializedName("0")
    val x0: String
) : Parcelable

@Parcelize
data class History(
    @SerializedName("comment")
    val comment: String? = "",
    @SerializedName("date_added")
    val dateAdded: String? = "",
    @SerializedName("date_added_timestamp")
    val timestamp: String? = "",
    @SerializedName("status")
    val status: String,
    var isFound: Boolean = true
) : Parcelable {

    fun getDate() = getDateFromTimeStamp(timestamp)
}

@Parcelize
data class Total(
    @SerializedName("code")
    val code: String,
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("order_total_id")
    val orderTotalId: String,
    @SerializedName("sort_order")
    val sortOrder: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("value")
    val value: String
) : Parcelable