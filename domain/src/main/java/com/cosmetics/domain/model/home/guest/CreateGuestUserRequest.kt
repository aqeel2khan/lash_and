package com.cosmetics.domain.model.home.guest


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateGuestUserRequest(
    @SerializedName("address_1")
    val address1: String,
    @SerializedName("address_2")
    val address2: String?,
    @SerializedName("city")
    val city: String,
    @SerializedName("company")
    val company: String?,
    @SerializedName("country_id")
    val countryId: Int?,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("zone_id")
    val zoneId: Int?,
    @SerializedName("avenue")
    val avenue: String?,
    @SerializedName("floor")
    val floor: String,
    @SerializedName("flat")
    val flat: String
) : Parcelable, BaseResponse()