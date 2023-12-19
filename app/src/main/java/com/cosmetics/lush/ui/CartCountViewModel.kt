package com.cosmetics.lush.ui

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.interaction.product.cart.GetCartCountUseCase
import com.cosmetics.domain.model.Results

class CartCountViewModel(private val getCartCountUseCase: GetCartCountUseCase) : BaseViewModel() {
    var cartCount = MutableLiveData(0)
    fun getCartCount() {
        executeUseCase(false) {
            getCartCountUseCase("")
                .onSuccess {
                    val response = Results.Success(it).data
                    with(response.cartCount) {
                        cartCount.value = totalProductCount
                        PreferenceDelegate.cartCountProducts = totalProductCount
                    }
                }
        }
    }

    override fun getBindingId(): Int = 0
}