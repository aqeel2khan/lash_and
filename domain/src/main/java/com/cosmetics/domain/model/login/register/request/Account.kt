package com.cosmetics.domain.model.login.register.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account(
    @SerializedName("1")
    val x1: String
) : Parcelable