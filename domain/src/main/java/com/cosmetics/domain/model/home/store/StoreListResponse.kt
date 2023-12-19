package com.cosmetics.domain.model.home.store

import android.os.Parcelable
import androidx.core.text.HtmlCompat
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreListResponse(
    @SerializedName("data")
    val store: List<Store>
) : Parcelable, BaseResponse()

@Parcelize
data class Store(
    @SerializedName("address")
    val address: String,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("fax")
    val fax: String,
    @SerializedName("geocode")
    val geocode: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("location_id")
    val locationId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("open")
    val `open`: String,
    @SerializedName("telephone")
    val telephone: String
) : Parcelable {
    fun getAddressInText(): String = address.valueFromHtml()
    fun getOpenTime(): String = open.valueFromHtml()

}

fun String.valueFromHtml() =
    HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim()