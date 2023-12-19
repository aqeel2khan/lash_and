package com.cosmetics.domain.model.home.profile


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressResponse(
    @SerializedName("data")
    val addressList: AddressList
) : Parcelable, BaseResponse()


data class SetCartAddressRequest(
    @SerializedName("address_id")
    var address_id: Int,
    var isPaymentAddress: Boolean
)

data class CurrentSelectedDeliveryAddress(
    @SerializedName("address_id")
    var address_id: Int,
    var isDeliveryAddress: Boolean,
    var fullAddress: String = "",
    var name: String = ""
)


@Parcelize
data class AddressList(

    @SerializedName("address_id")
    val addressId: Int,
    @SerializedName("addresses")
    var addresses: List<Address>
) : Parcelable

@Parcelize
data class Address(
    @SerializedName("address_1")
    val address1: String,
    @SerializedName("address_2")
    val address2: String,
    @SerializedName("address_id")
    val addressId: Int,
    @SerializedName("city")
    val city: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("zone")
    val zone: String,
    @SerializedName("zone_code")
    val zoneCode: String,
    @SerializedName("zone_id")
    val zoneId: Int,
    @SerializedName("avenue")
    val avenue: String,
    @SerializedName("floor")
    val floor: String,
    @SerializedName("flat")
    val flat: String,
    var isSelected: Boolean = false
) : Parcelable {


    fun getFullAddress(): String {
        var addressData = ""
        if (!company.isNullOrEmpty()) addressData = "$addressData$company, "
        if (!address1.isNullOrEmpty()) addressData = "$addressData$address1, "
        if (!address2.isNullOrEmpty()) addressData = "$addressData$address2, "
        if (!city.isNullOrEmpty()) addressData = "$addressData$city, "
        if (!avenue.isNullOrEmpty()) addressData = "$addressData$avenue, "
        if (!floor.isNullOrEmpty()) addressData = "$addressData$floor, "
        if (!flat.isNullOrEmpty()) addressData = "$addressData$flat, "
        if (!zone.isNullOrEmpty()) addressData = "$addressData$zone, "
        if (!country.isNullOrEmpty()) addressData = "$addressData$country "
        return addressData
    }

    fun getUserName(): String {
        return "$firstname $lastname"
    }

}

