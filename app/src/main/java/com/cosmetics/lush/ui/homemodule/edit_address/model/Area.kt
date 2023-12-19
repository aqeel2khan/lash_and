package com.cosmetics.lush.ui.homemodule.edit_address.model


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Area(
    @SerializedName("title")
    val title: String
) : Parcelable