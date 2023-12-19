package com.cosmetics.lush.ui.homemodule.profile

import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.domain.interaction.profile.address.GetAddressUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.AddressList

class AddressListViewModel(private val getAddressUseCase: GetAddressUseCase) : BaseViewModel() {
    override fun getBindingId(): Int = 0
    var addressResponse = MutableLiveData<AddressList>()

    fun getAddressList(): MutableLiveData<AddressList> {
        executeUseCase(false) {
            getAddressUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    addressResponse.postValue(response.addressList)
                }
                .onFailure {
                    handleError(it) {
                        getAddressList()
                    }
                }
        }
        return addressResponse
    }
}
