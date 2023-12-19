package com.cosmetics.domain.model.home.request


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.home.store.valueFromHtml
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeResponse(
    @SerializedName("data")
    var data: List<Category>
) : Parcelable, BaseResponse()

@Parcelize
data class Category(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_image")
    val originalImage: String,
    @SerializedName("parent_id")
    val parentId: Int,
    @SerializedName("seo_url")
    val seoUrl: String,
    @SerializedName("product_count")
    val productCount: Int
) : Parcelable {

    val formattedName: String
        get() = name.valueFromHtml()

    fun getNumberOfSubCategories() = categories.size

}

