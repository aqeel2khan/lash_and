package com.cosmetics.domain.model.login.register.response


import android.annotation.SuppressLint
import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class RegistrationResponse(
    @SerializedName("data")
    val data: Data
) : Parcelable, BaseResponse()