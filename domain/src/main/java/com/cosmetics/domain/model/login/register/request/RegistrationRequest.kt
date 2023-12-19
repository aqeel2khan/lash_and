package com.cosmetics.domain.model.login.register.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistrationRequest(
    @SerializedName("email")
    var email: String = "",
    @SerializedName("firstname")
    var firstName: String = "",
    @SerializedName("lastname")
    var lastName: String = "",
    @SerializedName("password")
    var password: String = "",
    @SerializedName("confirm")
    var confirmPassword: String = "",
    @SerializedName("agree")
    var agree: String = "1",
    @SerializedName("telephone")
    var mobileNo: String = ""
) : Parcelable


/*@Parcelize
data class RegistrationRequest(
    @SerializedName("agree")
    val agree: String,
    @SerializedName("confirm")
    val confirm: String,
    @SerializedName("custom_field")
    val customField: CustomField,
    @SerializedName("customer_group_id")
    val customerGroupId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("telephone")
    val telephone: String
) : Parcelable*/