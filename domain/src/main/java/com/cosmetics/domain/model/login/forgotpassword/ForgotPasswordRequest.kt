package com.cosmetics.domain.model.login.forgotpassword

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("email")
    var email: String = ""
)