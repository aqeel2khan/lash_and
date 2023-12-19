package com.cosmetics.domain.model.product


import android.content.Context
import android.os.Parcelable
import com.cosmetics.domain.R
import com.cosmetics.domain.di.getKoinInstance
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

private val context: Context = getKoinInstance()

@Parcelize
data class Option(
    @SerializedName("name")
    val name: String,
    @SerializedName("option_id")
    val optionId: String,
    @SerializedName("option_value")
    val optionValue: List<OptionValue>,
    @SerializedName("product_option_id")
    val productOptionId: Int,
    @SerializedName("required")
    val required: Boolean,
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    var value: String,
    var activePositionIndex: Int = 0,
) : Parcelable {
    fun getOptionValueNameList(totalPrice: String): Array<String>? {
        var lst = mutableListOf<String>()
        lst.add(context.getString(R.string.please_select))
        lst.addAll(optionValue.map { it.getOptionNameWitPrice(totalPrice) })
        return lst.toTypedArray()
    }

    fun getOptionValueName(): String =
        if (value.isNullOrEmpty()) {
            String.format(context.getString(R.string.select_item), name)
        } else {
            value
        }

}

@Parcelize
data class OptionValue(
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("option_value_id")
    val optionValueId: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("price_excluding_tax")
    val priceExcludingTax: String,
    @SerializedName("price_excluding_tax_formated")
    val priceExcludingTaxFormated: String,
    @SerializedName("price_formated")
    val priceFormated: String,
    @SerializedName("price_prefix")
    val pricePrefix: String,
    @SerializedName("product_option_value_id")
    val productOptionValueId: String,
    @SerializedName("quantity")
    val quantity: Int,
    var isChecked: Boolean = false
) : Parcelable {


    fun getOptionNameWitPrice(totalPrice: String) = try {
        val totalPriceNumeric = totalPrice.toDouble()
        val optionValuePrice = price.toDouble()
        "KD ${totalPriceNumeric + optionValuePrice}/ $name "
    } catch (ex: Exception) {
        "$pricePrefix+ $priceFormated/ $name "
    }
}

