package com.cosmetics.lush.ui.login.register

import android.text.TextUtils
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import com.cosmetics.core.base.onFailure
import com.cosmetics.core.base.onSuccess
import com.cosmetics.core.utils.BaseViewModel
import com.cosmetics.core.utils.Completed
import com.cosmetics.core.utils.Event
import com.cosmetics.core.utils.isEmail
import com.cosmetics.domain.di.PreferenceDelegate
import com.cosmetics.domain.interaction.account.register.GetRegisterUseCase
import com.cosmetics.domain.model.Results
import com.cosmetics.domain.model.login.register.request.RegistrationRequest
import com.cosmetics.domain.model.login.register.response.RegistrationResponse
import com.cosmetics.lush.R

class RegisterViewModel(private val getRegisterUseCase: GetRegisterUseCase) :
    BaseViewModel() {

    val registerValidationModel = RegistrationRequest()

    val nameErrorLiveData = MutableLiveData<Int>()
    val lastNameErrorLiveData = MutableLiveData<Int>()
    val emailErrorLiveData = MutableLiveData<Int>()
    val passwordErrorLiveData = MutableLiveData<Int>()
    val confirmPasswordErrorLiveData = MutableLiveData<Int>()
    val mobileNoErrorLiveData = MutableLiveData<Int>()

    val successLiveData = MutableLiveData<Event<Boolean>>()

    override fun getBindingId(): Int = BR.viewModel

    fun register() {
        if (TextUtils.isEmpty(registerValidationModel.firstName)) {
            nameErrorLiveData.postValue(R.string.name_error)
        } else if (TextUtils.isEmpty(registerValidationModel.lastName)) {
            lastNameErrorLiveData.postValue(R.string.last_name_error)
        } else if (!registerValidationModel.email.isEmail()) {
            emailErrorLiveData.postValue(R.string.email_error)
        } else if (TextUtils.isEmpty(registerValidationModel.password)) {
            passwordErrorLiveData.postValue(R.string.password_empty_error)
        } else if (TextUtils.isEmpty(registerValidationModel.confirmPassword) || isPasswordDoesNotMatch()) {
            confirmPasswordErrorLiveData.postValue(R.string.passwords_does_not_match)
        } else if (TextUtils.isEmpty(registerValidationModel.mobileNo)) {
            mobileNoErrorLiveData.postValue(R.string.mobile_error)
        } else {
            clearErrors()
            callRegisterService()
        }
    }

    private fun isPasswordDoesNotMatch(): Boolean =
        with(registerValidationModel) {
            confirmPassword != password
        }


    private fun callRegisterService() {
        executeUseCase(true) {
            getRegisterUseCase(registerValidationModel)
                .onSuccess {
                    viewState.value = Completed
                    val response = Results.Success(it).data
                    saveToPreference(response)
                    successLiveData.postValue(Event(true))
                }
                .onFailure {
                    PreferenceDelegate.token = ""
                    handleError(it)
                }
        }
    }

    private fun saveToPreference(response: RegistrationResponse) {
        PreferenceDelegate.firstName = response.data.firstname
        PreferenceDelegate.lastName = response.data.lastname
        PreferenceDelegate.email = response.data.email
        PreferenceDelegate.telephone = response.data.telephone
        PreferenceDelegate.customerId = response.data.customerId
    }
    private fun clearErrors() {
        nameErrorLiveData.postValue(0)
        emailErrorLiveData.postValue(0)
        passwordErrorLiveData.postValue(0)
        mobileNoErrorLiveData.postValue(0)
    }
}
