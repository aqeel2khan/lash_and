package com.cosmetics.domain.model.login.userlogin


import android.annotation.SuppressLint
import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class LoginResponse(
    @SerializedName("data")
    val data: Data
) : Parcelable, BaseResponse()


@SuppressLint("ParcelCreator")
@Parcelize
data class Data(
    @SerializedName("account_custom_field")
    val accountCustomField: AccountCustomField,
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("cart_count_products")
    val cartCountProducts: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("custom_fields")
    val customFields: List<String>,
    @SerializedName("customer_group_id")
    val customerGroupId: String,
    @SerializedName("customer_id")
    val customerId: String,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("fax")
    val fax: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("ip")
    val ip: String,
    @SerializedName("language_id")
    val languageId: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("newsletter")
    val newsletter: String,
    @SerializedName("safe")
    val safe: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("store_id")
    val storeId: String,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("wishlist")
    val wishlist: List<String>,
    @SerializedName("wishlist_total")
    val wishlistTotal: String
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AccountCustomField(
    @SerializedName("1")
    val x1: String
) : Parcelable