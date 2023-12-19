package com.cosmetics.lush.ui.homemodule.edit_address.model


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class GovernorateAreaModel(
    @SerializedName("areas")
    val governorateList: List<Governorate>
) : Parcelable