package com.cosmetics.domain.model.home.address


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryListResponse(
    @SerializedName("data")
    val country: List<Country>
) : Parcelable, BaseResponse()


@Parcelize
data class Country(
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
    val status: String
) : Parcelable