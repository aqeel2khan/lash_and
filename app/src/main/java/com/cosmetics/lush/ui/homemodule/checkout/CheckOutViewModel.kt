package com.cosmetics.lush.ui.homemodule.checkout

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.interaction.product.cart.ConfirmedOrderUseCase
import com.cosmetics.domain.interaction.product.cart.GetConfirmedProductUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.ConfirmOrderResponse
import com.cosmetics.domain.model.product.cart.ConfirmedProducts
import com.cosmetics.lush.BR

class CheckOutViewModel(
    private val getConfirmedProductUseCase: GetConfirmedProductUseCase,
    private val confirmedOrderUseCase: ConfirmedOrderUseCase
) :
    BaseViewModel() {
    override fun getBindingId(): Int = BR.viewModel
    var paymentMethodResponse = MutableLiveData<ConfirmedProducts>()
    var confirmOrderResponse = MutableLiveData<ConfirmOrderResponse>()
    var isDataAvailable = MutableLiveData<Boolean>(false)

    init {
        getConfirmedProduct()
    }

    private fun getConfirmedProduct(): MutableLiveData<ConfirmedProducts> {
        executeUseCase(true) {
            getConfirmedProductUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    paymentMethodResponse.value = response.data
                    isDataAvailable.value = true
                }
                .onFailure {
                    handleError(it) {
                        getConfirmedProduct()
                    }
                    isDataAvailable.value = false
                }
        }
        return paymentMethodResponse
    }

    fun confirmOrder() {
        executeUseCase(true) {
            confirmedOrderUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    confirmOrderResponse.value = response
                }
                .onFailure {
                    handleError(it)
                }
        }
    }


}
