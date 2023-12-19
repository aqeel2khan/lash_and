package com.cosmetics.domain.model.home.contactus

import com.google.gson.annotations.SerializedName

data class ContactRequest(
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("enquiry")
    var description: String
)