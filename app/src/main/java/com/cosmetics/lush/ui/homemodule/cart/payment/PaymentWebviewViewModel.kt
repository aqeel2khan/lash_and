package com.cosmetics.lush.ui.homemodule.cart.payment

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.interaction.order.GetKnetPayDetailsUseCase
import com.cosmetics.domain.interaction.product.cart.ConfirmedOrderUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.ConfirmOrderResponse

class PaymentWebViewViewModel(
    private val getKnetPayDetailsUseCase: GetKnetPayDetailsUseCase,
    private val confirmedOrderUseCase: ConfirmedOrderUseCase
) : BaseViewModel() {
    var webViewData = MutableLiveData<String>()
    var onlinePaymentStatus = MutableLiveData<Results<BaseResponse>>()
    var confirmOrderResponse = MutableLiveData<ConfirmOrderResponse>()

    override fun getBindingId(): Int = 0

    fun getPaymentData(): MutableLiveData<String> {
        executeUseCase {
            getKnetPayDetailsUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    webViewData.value = it
                }
                .onFailure {
                    handleError(it)
                }
        }
        return webViewData
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
                    handleError(it) {
                        confirmOrder()
                    }
                }
        }
    }

}