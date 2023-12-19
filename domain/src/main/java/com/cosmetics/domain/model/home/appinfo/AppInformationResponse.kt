package com.cosmetics.domain.model.home.appinfo


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.util.handleEscapeCharacter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppInformationResponse(
    @SerializedName("data")
    val appInformation: List<AppInformation>
) : Parcelable, BaseResponse()

@Parcelize
data class AppInformation(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
) : Parcelable {
    fun getFormattedTitle() = handleEscapeCharacter(title)
}

@Parcelize
data class AppInfo(
    val title: String,
    val fileName: String
) : Parcelable