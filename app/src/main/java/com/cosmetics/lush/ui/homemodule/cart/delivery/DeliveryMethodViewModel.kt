package com.cosmetics.lush.ui.homemodule.cart.delivery

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.base.BaseResponse
import com.cosmetics.domain.interaction.profile.address.GetDeliveryMethodUseCase
import com.cosmetics.domain.interaction.profile.address.SetDeliveryMethodUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.delivermethods.DeliveryMethodRequest
import com.cosmetics.domain.model.home.delivermethods.DeliveryMethods
import com.cosmetics.domain.model.home.delivermethods.Quote
import com.cosmetics.lush.BR
import com.cosmetics.lush.R

class DeliveryMethodViewModel(
    private val context: Context,
    private val getDeliveryMethodUseCase: GetDeliveryMethodUseCase,
    private val setDeliveryMethodUseCase: SetDeliveryMethodUseCase
) : BaseViewModel() {
    var deliveryMethodResponse = MutableLiveData<DeliveryMethods>()
    var isDataAvailable = MutableLiveData(false)
    var submitDeliveryMethod = MutableLiveData<BaseResponse>()
    var quoteSelected: Quote? = null
    var comments: String? = ""
    override fun getBindingId(): Int = BR.viewModel

    init {
        getDeliveryMethods()
    }


    private fun getDeliveryMethods(): MutableLiveData<DeliveryMethods> {
        executeUseCase(false) {
            getDeliveryMethodUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    deliveryMethodResponse.value = response.data
                    isDataAvailable.value = true
                }
                .onFailure {
                    isDataAvailable.value = true
                    handleError(it) {
                        getDeliveryMethods()
                    }
                }
        }
        return deliveryMethodResponse
    }

    private fun setDeliveryMethods(deliveryMethodRequest: DeliveryMethodRequest) {
        executeUseCase(true) {
            setDeliveryMethodUseCase(deliveryMethodRequest)
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    submitDeliveryMethod.value = response
                }
                .onFailure {
                    handleError(it)
                }
        }
    }

    fun onItemClick(quote: Quote) {
        val deliveryMethods = deliveryMethodResponse.value
        deliveryMethods?.let { deliveryMethod ->
            deliveryMethod.shippingMethods.let { shippingMethods ->
                if (shippingMethods.isNotEmpty()) {
                    shippingMethods.forEach { shippingItem ->
                        if (!shippingItem.quote.isNullOrEmpty()) {
                            shippingItem.quote.forEach { it ->
                                it.isSelected = it.code == quote.code
                                if (it.isSelected) quoteSelected = it
                            }
                        }
                    }
                }
            }
        }
        deliveryMethodResponse.value = deliveryMethods
    }

    fun onContinueButtonClick() {
        if (isInputValid()) {
            setDeliveryMethods(DeliveryMethodRequest(comments!!, quoteSelected?.code!!))
        } else {
            handleError(context.getString(R.string.please_select_delivery_method))
        }
    }

    private fun isInputValid(): Boolean = quoteSelected != null
    /* if (comments.isNullOrEmpty()) {
         false
     } else */

}
