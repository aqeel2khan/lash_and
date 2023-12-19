package com.cosmetics.domain.model.home.profile


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


const val NORMAL_ADDRESS = 1
const val EDIT_ADDRESS = 2
const val DELIVERY_ADDRESS = 3
const val PAYMENT_ADDRESS = 4
const val GUEST_CREATE_USER = 5
const val GUEST_SHIPPING = 6

@Parcelize
data class UpdateAddressRequest(
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname: String = ".",
    @SerializedName("company")
    val company: String?,
    @SerializedName("address_1")
    val address1: String,
    @SerializedName("address_2")
    val address2: String?,
    @SerializedName("city")
    val city: String,
    @SerializedName("country_id")
    val countryId: Int?,
    @SerializedName("zone_id")
    val zoneId: Int?,
    @SerializedName("avenue")
    val avenue: String?,
    @SerializedName("floor")
    val floor: String?,
    @SerializedName("flat")
    val flat: String?,
    val addressId: String = "",
    val type: Int = NORMAL_ADDRESS
) : Parcelable