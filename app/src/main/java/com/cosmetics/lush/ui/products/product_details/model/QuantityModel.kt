package com.cosmetics.lush.ui.products.product_details.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.cosmetics.domain.di.getKoinInstance
import com.cosmetics.lush.R

class QuantityModel {
    var countValue = MutableLiveData("1")
    var minimum = 1
    var totalCount = 1
    var errorMessage = MutableLiveData<String>()
    var context: Context = getKoinInstance()
    fun increment() {
        totalCount += 1
        countValue.value = totalCount.toString()
    }

    fun decrement() {
        if (totalCount <= minimum) {
            errorMessage.value = String.format(
                context.getString(R.string.this_product_has_a_minmium_value),
                minimum
            )
        } else {
            totalCount -= 1
            countValue.value = totalCount.toString()
        }
    }


}
