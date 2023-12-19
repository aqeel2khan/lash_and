package com.cosmetics.domain.model.login.register.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomField(
    @SerializedName("account")
    val account: Account
) : Parcelable