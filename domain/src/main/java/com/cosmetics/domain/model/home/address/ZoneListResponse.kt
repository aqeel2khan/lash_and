package com.cosmetics.domain.model.home.address


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ZoneListResponse(
    @SerializedName("data")
    val data: Data
) : Parcelable, BaseResponse()

@Parcelize
data class Data(
    @SerializedName("address_format")
    val addressFormat: String,
    @SerializedName("country_id")
    val countryId: Int,
    @SerializedName("iso_code_2")
    val isoCode2: String,
    @SerializedName("iso_code_3")
    val isoCode3: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("postcode_required")
    val postcodeRequired: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("zone")
    val zone: List<Zone>
) : Parcelable

@Parcelize
data class Zone(
    @SerializedName("code")
    val code: String,
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("zone_id")
    val zoneId: Int
) : Parcelable