package com.cosmetics.domain.model.home.profile


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfileResponse(
    @SerializedName("data")
    val userProfile: UserProfile
) : Parcelable, BaseResponse()


@Parcelize
data class UserProfile(
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("cart")
    val cart: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("customer_group_id")
    val customerGroupId: String,
    @SerializedName("customer_id")
    val customerId: String,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("fax")
    val fax: String,
    @SerializedName("firstname")
    var firstname: String,
    @SerializedName("ip")
    val ip: String,
    @SerializedName("language_id")
    val languageId: String,
    @SerializedName("lastname")
    var lastname: String,
    @SerializedName("newsletter")
    val newsletter: String,
    @SerializedName("reward_total")
    val rewardTotal: String,
    @SerializedName("safe")
    val safe: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("store_id")
    val storeId: String,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("user_balance")
    val userBalance: String,
    @SerializedName("wishlist")
    val wishlist: String
) : Parcelable {
    fun getUserName(): String {
        return "$firstname $lastname"
    }
}