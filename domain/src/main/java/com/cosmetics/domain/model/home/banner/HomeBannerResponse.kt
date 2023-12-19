package com.cosmetics.domain.model.home.banner


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeBannerResponse(
    @SerializedName("data")
    val data: List<Data>
) : Parcelable, BaseResponse()