package com.cosmetics.lush.ui.homemodule.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.Loading
import com.cosmetics.core.utils.ViewState
import com.cosmetics.domain.base.NO_DATA_FOUND
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.interaction.profile.UpdateUserProfileUseCase
import com.cosmetics.domain.interaction.profile.address.GetAddressUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.home.profile.Address
import com.cosmetics.domain.model.home.profile.AddressList
import com.cosmetics.domain.model.home.profile.UpdateUserProfileRequest
import com.cosmetics.lush.BR
import com.cosmetics.lush.R
import com.cosmetics.lush.ui.getUserName

private fun getFirstName(): String? =
    PreferenceDelegate.firstName

private fun getLastName(): String? =
    PreferenceDelegate.lastName


class ProfileViewModel(
    private val context: Context,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val getAddressUseCase: GetAddressUseCase
) : BaseViewModel() {
    var firstName = MutableLiveData<String>(getFirstName())
    var lastName = MutableLiveData<String>(getLastName())
    var email = MutableLiveData<String>(PreferenceDelegate.email)
    var telephone = MutableLiveData<String>(PreferenceDelegate.telephone)
    var nameDisplayed = MutableLiveData<String>(getUserName())
    var emailDisplayed = MutableLiveData<String>(PreferenceDelegate.email)

    var error = MutableLiveData<String>()
    var isSuccess = MutableLiveData<UpdateUserProfileRequest>()
    var address = MutableLiveData<Address>()
    var addressResponse: AddressList? = null
    var count = MutableLiveData<String>("0")

    //  var isAddressListAvailable = MutableLiveData<String>("0")
    var getProgressState = MutableLiveData<ViewState>(Loading)
    override fun getBindingId(): Int = BR.viewModel

    fun isProgressNeeded(state: ViewState) =
        state == Loading

    fun isAddressContentNotNeeded(state: ViewState) =
        state == Loading || address.value == null


    fun saveProfile() {
        if (isRequestValid()) {
            val updateUserProfileRequest = UpdateUserProfileRequest(
                firstname = firstName.value!!,
                lastname = lastName.value ?: " ",
                email = email.value!!,
                telephone = telephone.value!!
            )
            executeUseCase {
                updateUserProfileUseCase(updateUserProfileRequest)
                    .onSuccess {
                        viewState.value = Completed
                        val response = Results.Success(it).data
                        emailDisplayed.value = updateUserProfileRequest.email
                        saveUserInfo(updateUserProfileRequest)
                        nameDisplayed.value = getUserName()
                        isSuccess.postValue(updateUserProfileRequest)
                    }
                    .onFailure { handleError(it) }
            }
        }
    }

    fun getAddressList(): MutableLiveData<Address> {
        executeUseCase(false) {
            getAddressUseCase("")
                .onSuccess {
                    viewState.value = Completed
                    getProgressState.value = Completed
                    val response = Results.Success(it).data
                    if (!response.addressList.addresses.isNullOrEmpty()) {
                        address.value = response.addressList.addresses[0]
                        addressResponse = response.addressList
                        count.value = ((response.addressList.addresses.size) - 1).toString()
                    }
                }
                .onFailure {
                    if (it != NO_DATA_FOUND) {
                        handleError(it) {
                            getAddressList()
                        }
                    }
                    getProgressState.value = Completed
                }
        }
        return address
    }

    private fun saveUserInfo(updateUserProfileRequest: UpdateUserProfileRequest) {
        PreferenceDelegate.firstName = updateUserProfileRequest.firstname
        PreferenceDelegate.lastName = updateUserProfileRequest.lastname
        PreferenceDelegate.email = updateUserProfileRequest.email
        PreferenceDelegate.telephone = updateUserProfileRequest.telephone
    }

    private fun isRequestValid(): Boolean {
        return when {
            firstName.value.isNullOrEmpty() -> {
                error.value = context.getString(R.string.error_name_field_empty)
                false
            }
            lastName.value.isNullOrEmpty() -> {
                error.value = context.getString(R.string.error_last_name_field_empty)
                false
            }
            email.value.isNullOrEmpty() -> {
                error.value = context.getString(R.string.error_email_field_empty)
                false
            }
            telephone.value.isNullOrEmpty() -> {
                error.value = context.getString(R.string.error_telephone_field_empty)
                false
            }
            /*    firstName.value?.trim() == getFirstName()?.trim() &&
                        lastName.value?.trim() == getLastName()?.trim() &&
                        email.value?.trim() == PreferenceDelegate.email.trim() &&
                        telephone.value?.trim() == PreferenceDelegate.telephone.trim() -> {
                    error.value = context.getString(R.string.error_input_is_same)
                    false
                }*/
            else -> {
                true
            }
        }
    }
}