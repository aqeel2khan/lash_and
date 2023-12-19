package com.cosmetics.lush.ui.homemodule.cart.address

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.base.NO_DATA_FOUND
import com.cosmetics.domain.interaction.profile.address.GetDeliveryAddressUseCase
import com.cosmetics.domain.interaction.profile.address.SetDeliveryAddressUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.Address
import com.cosmetics.domain.model.home.profile.AddressList
import com.cosmetics.domain.model.home.profile.AddressResponse
import com.cosmetics.domain.model.home.profile.SetCartAddressRequest
import com.cosmetics.lush.BR
import com.cosmetics.lush.R

class CartAddressViewModel(
    private val context: Context,
    private val getDeliveryAddressUseCase: GetDeliveryAddressUseCase,
    private val setDeliveryAddressUseCase: SetDeliveryAddressUseCase,
    private val isShippingAddressNeeded: Boolean
) : BaseViewModel() {
    override fun getBindingId(): Int = BR.viewModel
    var deliveryAddressResponse = MutableLiveData<AddressList>()
    var navigateToPaymentSelection = MutableLiveData<Boolean>(false)
    var paymentAddressResponse = MutableLiveData<AddressList>()
    var deliverySelectedAddress = MutableLiveData<Address>()
    var paymentSelectedAddress = MutableLiveData<Address>()
    var isShippingInfoNeeded = MutableLiveData<Boolean>(isShippingAddressNeeded)
    var isPaymentAddressRegistered = MutableLiveData(false)
    var isDeliveryAddressRegistered = MutableLiveData(false)
    var isPaymentAddressSelectionActive = MutableLiveData(true)
    var isRetryForDeliveryAddress = MutableLiveData(false)
    var isRetryForPaymentAddress = MutableLiveData(false)
    var isDeliveryAddressAvailable = MutableLiveData(true)
    var isPaymentAddressAvailable = MutableLiveData(true)

    fun callAPI() {
        getDeliveryAddressList(true)
        getDeliveryAddressList(false)
    }

    private fun getDeliveryAddressList(isDeliverAddressSelection: Boolean): MutableLiveData<AddressList> {
        executeUseCase(true) {
            getDeliveryAddressUseCase(isDeliverAddressSelection)
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    handleAddressListResponse(response, isDeliverAddressSelection)
                }
                .onFailure {
                    var message: String = it
                    if (isDeliverAddressSelection) {
                        if (message == NO_DATA_FOUND) {
                            message = context.getString(R.string.no_address_found)
                        }
                        handleError(message)
                        if (it != NO_DATA_FOUND)
                            isRetryForDeliveryAddress.postValue(true)
                    } else {
                        isRetryForPaymentAddress.postValue(true)
                    }
                }
        }
        return deliveryAddressResponse
    }

    private fun handleAddressListResponse(
        response: AddressResponse,
        isPaymentAddressSelection: Boolean
    ) {
        if (isPaymentAddressSelection) {
            isRetryForPaymentAddress.postValue(false)
            if (response.addressList.addresses.isNullOrEmpty()) {
                isPaymentAddressAvailable.value = false
            } else {
                getUpdatedList(
                    isPaymentAddressSelection,
                    response.addressList.addressId,
                    response.addressList
                )?.let { addressList ->
                    paymentAddressResponse.value = addressList
                }
            }
        } else {
            isRetryForDeliveryAddress.postValue(false)
            if (response.addressList.addresses.isNullOrEmpty()) {
                isDeliveryAddressAvailable.value = false
            } else {
                getUpdatedList(
                    isPaymentAddressSelection,
                    response.addressList.addressId,
                    response.addressList
                )?.let { addressList ->
                    deliveryAddressResponse.value = addressList
                }
            }

        }
    }

    fun callCartAddressRequest() {
        var addressId: Int? = if (isPaymentAddressSelectionActive.value!!)
            paymentSelectedAddress.value?.addressId else deliverySelectedAddress.value?.addressId
        var cartAddressRequest: SetCartAddressRequest? = addressId?.let {
            SetCartAddressRequest(it, isPaymentAddressSelectionActive.value!!)
        }
        if (cartAddressRequest == null) {
            handleError(context.getString(R.string.please_select_address))
        }
        cartAddressRequest?.let { it ->
            executeUseCase(true) {
                setDeliveryAddressUseCase(it)
                    .onSuccess {
                        viewState.value = Completed
                        val response = Results.Success(it).data
                        if (isPaymentAddressSelectionActive.value!!) {
                            isPaymentAddressSelectionActive.value = false
                            isPaymentAddressRegistered.value = true
                            cartAddressRequest = null
                            if (!isShippingAddressNeeded) {
                                navigateToPaymentSelection.value = true
                            }
                        } else {
                            isDeliveryAddressRegistered.value = true
                        }
                    }
                    .onFailure {
                        handleError(it)
                    }
            }
        }
    }

    fun onItemClick(address: Address) {
        if (isPaymentAddressSelectionActive.value!!) {
            paymentSelectedAddress.value = address
            paymentAddressResponse.value =
                getUpdatedList(
                    isPaymentAddressSelectionActive.value!!,
                    address.addressId,
                    paymentAddressResponse.value
                )
        } else {
            deliverySelectedAddress.value = address
            deliveryAddressResponse.value =
                getUpdatedList(
                    isPaymentAddressSelectionActive.value!!,
                    address.addressId,
                    deliveryAddressResponse.value
                )
        }
    }

    private fun getUpdatedList(
        isPaymentAddressSelection: Boolean,
        addressId: Int?, value: AddressList?
    ): AddressList? {
        var address: Address? = null
        value?.let { addressList ->
            addressList.addresses.forEach {
                it.isSelected = addressId == it.addressId
                if (it.isSelected) address = it
            }
        }
        address?.let {
            if (isPaymentAddressSelection) {
                paymentSelectedAddress.value = it
            } else {
                deliverySelectedAddress.value = it
            }
        }
        return value
    }

    fun updateAddress() {
        isPaymentAddressRegistered.value = false
        isPaymentAddressSelectionActive.value = true
    }

    fun retry() {
        getDeliveryAddressList(false)
        getDeliveryAddressList(true)
    }

}
