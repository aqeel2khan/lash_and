package com.cosmetics.domain.model.login.register.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerGroup(
    @SerializedName("approval")
    val approval: String,
    @SerializedName("customer_group_id")
    val customerGroupId: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("language_id")
    val languageId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("sort_order")
    val sortOrder: String
) : Parcelable