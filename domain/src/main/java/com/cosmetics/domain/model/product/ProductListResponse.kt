package com.cosmetics.domain.model.product


import android.os.Parcelable
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.model.home.store.valueFromHtml
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat

@Parcelize
data class ProductListResponse(
    @SerializedName("data")
    val products: MutableList<Product>?
) : Parcelable, BaseResponse() {
    var searchKey = ""
}

const val WISH_LIST_GONE = 0
const val WISH_LIST_ADDED = 1
const val WISH_LIST_NOT_ADDED = 2

fun getPriceFormatted(price: Float): String = try {
    DecimalFormat("#,###.00").format(price)
} catch (e: Exception) {
    price.toString()
}


@Parcelize
data class Product(
    @SerializedName("attribute_groups")
    val attributeGroups: List<AttributeGroup>,
    @SerializedName("category")
    val category: List<ProductCategory>,
    @SerializedName("date_added")
    val dateAdded: String?,
    @SerializedName("date_available")
    val dateAvailable: String?,
    @SerializedName("date_modified")
    val dateModified: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("discounts")
    val discounts: List<Discount>,
    @SerializedName("ean")
    val ean: String?,
    @SerializedName("height")
    val height: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("jan")
    val jan: String?,
    @SerializedName("length")
    val length: String?,
    @SerializedName("length_class")
    val lengthClass: String?,
    @SerializedName("length_class_id")
    val lengthClassId: Int?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("manufacturer")
    val manufacturer: String?,
    @SerializedName("manufacturer_id")
    val manufacturerId: Int?,
    @SerializedName("meta_description")
    val metaDescription: String?,
    @SerializedName("meta_keyword")
    val metaKeyword: String?,
    @SerializedName("meta_title")
    val metaTitle: String?,
    @SerializedName("minimum")
    val minimum: String?,
    @SerializedName("model")
    val model: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("options")
    val options: List<Option>,
    @SerializedName("original_image")
    val originalImage: String,
    @SerializedName("original_images")
    val originalImages: List<String>,
    @SerializedName("price")
    val price: String,
    @SerializedName("price_excluding_tax")
    val priceExcludingTax: Float,
    @SerializedName("price_excluding_tax_formated")
    val priceExcludingTaxFormated: String,
    @SerializedName("price_formated")
    val priceFormated: String,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("rating")
    val rating: Int = 0,
    @SerializedName("reviews")
    val reviews: Reviews,
    @SerializedName("sort_order")
    val sortOrder: String,
    @SerializedName("special_excluding_tax")
    val specialExcludingTax: Float,
    @SerializedName("special_excluding_tax_formated")
    val specialExcludingTaxFormated: String,
    @SerializedName("stock_status")
    val stockStatus: String,
    @SerializedName("viewed")
    val viewed: String?,
    @SerializedName("weight")
    val weight: String?,
    @SerializedName("weight_class")
    val weightClass: String?,
    @SerializedName("weight_class_id")
    val weightClassId: String?,
    @SerializedName("width")
    val width: String?,
    var wishListStatus: Int = WISH_LIST_GONE
) : Parcelable, BaseResponse() {

    val formattedName: String
        get() = name.valueFromHtml()

    fun clone(): Product {
        val stringProject = Gson().toJson(this, Product::class.java)
        return Gson().fromJson(stringProject, Product::class.java)
    }

    fun isDiscountAvailable() = !discounts.isNullOrEmpty() && !isSpecialPriceAvailable()

    fun getImageList(): MutableList<String> {
        var list = mutableListOf<String>()
        if (image.isNotEmpty()) {
            list.add(image)
        }
        if (!images.isNullOrEmpty()) {
            list.addAll(images)
        }
        return list
    }

    fun isSpecialPriceAvailable(): Boolean {
        if (specialExcludingTax > 0) {
            return true
        }
        return false
    }

    fun getFinalePriceWithFormatted(): String {
        if (isSpecialPriceAvailable()) {
            return specialExcludingTaxFormated.trim()
        }
        return priceExcludingTaxFormated.trim()
    }

    fun getFinalePrice(): Float {
        if (isSpecialPriceAvailable()) {
            return specialExcludingTax
        }
        return priceExcludingTax
    }
}

@Parcelize
data class ProductCategory(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) : Parcelable


@Parcelize
data class Reviews(
    @SerializedName("review_total")
    val reviewTotal: Int,
    @SerializedName("reviews")
    val reviewList: List<ReviewItem>?
) : Parcelable

@Parcelize
data class ReviewItem(
    @SerializedName("author")
    val author: String,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("rating")
    val rating: Int = 0,
    @SerializedName("text")
    val text: String
) : Parcelable


@Parcelize
data class Attribute(
    @SerializedName("attribute_id")
    val attributeId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("text")
    val text: String,
    var isExpanded: Boolean = true
) : Parcelable

@Parcelize
data class AttributeGroup(
    @SerializedName("attribute_group_id")
    val attributeId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("attribute")
    val attributes: List<Attribute>
) : Parcelable


@Parcelize
data class Discount(
    @SerializedName("price")
    val price: Float,
    @SerializedName("price_excluding_tax")
    val priceExcludingTax: Float,
    @SerializedName("price_excluding_tax_formated")
    val priceExcludingTaxFormated: String,
    @SerializedName("price_formated")
    val priceFormated: String,
    @SerializedName("quantity")
    val quantity: Int,
    var isDiscountShown: Boolean = false
) : Parcelable