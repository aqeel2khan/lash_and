package com.cosmetics.domain.model.login.token


import android.annotation.SuppressLint
import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class GetTokenResponse(
    @SerializedName("data")
    val data: Data
) : Parcelable, BaseResponse()

@Parcelize
data class Data(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("token_type")
    val tokenType: String
) : Parcelable