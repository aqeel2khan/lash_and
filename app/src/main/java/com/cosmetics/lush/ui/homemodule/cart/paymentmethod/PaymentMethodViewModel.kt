package com.cosmetics.lush.ui.homemodule.cart.paymentmethod

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.interaction.product.cart.GetPaymentMethodUseCase
import com.cosmetics.domain.interaction.product.cart.SetPaymentMethodUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.product.cart.PaymentMethod
import com.cosmetics.domain.model.product.cart.SetPaymentMethodRequest
import com.cosmetics.lush.BR
import com.cosmetics.lush.R

class PaymentMethodViewModel(
    private val context: Context,
    private val getPaymentMethodUseCase: GetPaymentMethodUseCase,
    private val setPaymentMethodUseCase: SetPaymentMethodUseCase
) : BaseViewModel() {
    var isDataAvailable = MutableLiveData(false)
    var paymentMethodResponse = MutableLiveData<List<PaymentMethod>>()
    var submitPaymentMethod = MutableLiveData<SetPaymentMethodRequest>()
    var paymentMethodSelected: PaymentMethod? = null
    var comments: String? = ""
    override fun getBindingId(): Int = BR.viewModel

    init {
        getPaymentMethods()
    }


    private fun getPaymentMethods(): MutableLiveData<List<PaymentMethod>> {
        executeUseCase(false) {
            getPaymentMethodUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    isDataAvailable.value = true
                    val response = Results.Success(it).data
                    paymentMethodResponse.value = response.data.paymentMethods
                }
                .onFailure {
                    handleError(it)
                    isDataAvailable.value = true
                }
        }
        return paymentMethodResponse
    }

    private fun setDeliveryMethods(setPaymentMethodRequest: SetPaymentMethodRequest) {
        executeUseCase(true) {
            setPaymentMethodUseCase(setPaymentMethodRequest)
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    submitPaymentMethod.value = setPaymentMethodRequest
                }
                .onFailure {
                    handleError(it)
                }
        }
    }

    fun onItemClick(paymentMethod: PaymentMethod) {
        val paymentMethodList = paymentMethodResponse.value
        paymentMethodList?.let { item ->
            if (item.isNotEmpty()) {
                item.forEach {
                    it.isSelected = it.code == paymentMethod.code
                    if (it.isSelected) paymentMethodSelected = it
                }
            }
        }
        paymentMethodResponse.value = paymentMethodList
    }

    fun onContinueButtonClick() {
        if (isInputValid()) {
            setDeliveryMethods(
                SetPaymentMethodRequest(
                    comment = comments!!, paymentMethod = paymentMethodSelected?.code!!
                )
            )
        } else {
            handleError(context.getString(R.string.please_select_payment_method))
        }
    }

    private fun isInputValid(): Boolean = paymentMethodSelected != null
    /* if (comments.isNullOrEmpty()) {
         false
     } else */

}
