package com.cosmetics.domain.model.home.appinfo


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.util.handleEscapeCharacter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppInformationDetailsResponse(
    @SerializedName("data")
    val appInformationDetails: AppInformationDetails
) : Parcelable, BaseResponse()


@Parcelize
data class AppInformationDetails(
    @SerializedName("bottom")
    val bottom: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("information_id")
    val informationId: String,
    @SerializedName("language_id")
    val languageId: String,
    @SerializedName("meta_description")
    val metaDescription: String,
    @SerializedName("meta_keyword")
    val metaKeyword: String,
    @SerializedName("meta_title")
    val metaTitle: String,
    @SerializedName("sort_order")
    val sortOrder: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("store_id")
    val storeId: String,
    @SerializedName("title")
    val title: String
) : Parcelable {

    fun getHtmlDescription() =
        handleEscapeCharacter(description)

}