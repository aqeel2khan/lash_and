package com.cosmetics.domain.model.login.register.response


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Data(
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("custom_field")
    val customField: CustomField,
    @SerializedName("customer_group_id")
    val customerGroupId: String,
    @SerializedName("customer_groups")
    val customerGroups: List<CustomerGroup>,
    @SerializedName("customer_id")
    val customerId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("telephone")
    val telephone: String
) : Parcelable