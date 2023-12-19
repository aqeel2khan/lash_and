package com.cosmetics.domain.base

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName

val NO_DATA_FOUND = "No data found"
@SuppressLint("ParcelCreator")
open class BaseResponse {
    @SerializedName("error")
    val error: List<String> = emptyList()
    @SerializedName("success")
    val success: Int = 0
}

@SuppressLint("ParcelCreator")
open class BaseEmptyResponse {
    @SerializedName("error")
    val error: List<String> = mutableListOf(NO_DATA_FOUND)

    @SerializedName("success")
    val success: Int = 0
}
